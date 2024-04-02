package com.figure.core.db;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.jet.JetService;
import com.hazelcast.jet.Observable;
import com.hazelcast.jet.cdc.ChangeRecord;
import com.hazelcast.jet.cdc.mysql.MySqlCdcSources;
import com.hazelcast.jet.config.JobConfig;
import com.hazelcast.jet.function.Observer;
import com.hazelcast.jet.pipeline.Pipeline;
import com.hazelcast.jet.pipeline.Sinks;
import com.hazelcast.jet.pipeline.StreamSource;
import lombok.extern.slf4j.Slf4j;
import java.io.Serializable;


@Slf4j
public class MysqlMonitor implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private String databaseAddress;
    private Integer databasePort;
    private String databaseUser;
    private String databasePassword;
    private final String database;
    private String databaseTable;
    private final String keyColumn;

    public MysqlMonitor(String databaseAddress, Integer databasePort, String databaseUser, String databasePassword,String database , String databaseTable, String keyColumn) {
        this.databaseAddress = databaseAddress;
        this.databasePort = databasePort;
        this.databaseUser = databaseUser;
        this.databasePassword = databasePassword;
        this.database  = database;
        this.databaseTable = databaseTable;
        this.keyColumn = keyColumn;
    }

    public String getDatabaseAddress() {
        return databaseAddress;
    }

    public void setDatabaseAddress(String databaseAddress) {
        this.databaseAddress = databaseAddress;
    }

    public Integer getDatabasePort() {
        return databasePort;
    }

    public void setDatabasePort(Integer databasePort) {
        this.databasePort = databasePort;
    }

    public String getDatabaseUser() {
        return databaseUser;
    }

    public void setDatabaseUser(String databaseUser) {
        this.databaseUser = databaseUser;
    }

    public String getDatabasePassword() {
        return databasePassword;
    }

    public void setDatabasePassword(String databasePassword) {
        this.databasePassword = databasePassword;
    }

    public String getDatabaseTable() {
        return databaseTable;
    }

    public void setDatabaseTable(String databaseTable) {
        this.databaseTable = databaseTable;
    }


    public void startMonitor(CallBack cb){
        StreamSource<ChangeRecord> source = MySqlCdcSources.mysql("source")
                .setDatabaseAddress(databaseAddress)
                .setDatabasePort(databasePort)
                .setDatabaseUser(databaseUser)
                .setDatabasePassword(databasePassword)
                .setClusterName("dbserver1")
                .setDatabaseWhitelist(database)
                .setTableWhitelist(database+"."+databaseTable)
                .build();

        JobConfig cfg = new JobConfig().setName("mysql-monitor");
        HazelcastInstance hz = Hazelcast.bootstrappedInstance();

        JetService jet = hz.getJet();
        Observable<ChangeRecord> observable = jet.newObservable();
        observable.addObserver(new Observer<ChangeRecord>() {
            @Override
            public void onNext(ChangeRecord changeRecord) {
                String operation = changeRecord.operation().toString();
                if(operation=="DELETE"||operation=="INSERT"||operation=="UPDATE"){
                    System.out.println("changeRecord-->"+operation+"--"+changeRecord.timestamp());
                    cb.call();
                }
            }
        });

        Pipeline pipeline = Pipeline.create();
        pipeline.readFrom(source)
                .withoutTimestamps()
                .peek()
                .writeTo(Sinks.observable(observable));

        hz.getJet().newJob(pipeline, cfg);
    }

}
