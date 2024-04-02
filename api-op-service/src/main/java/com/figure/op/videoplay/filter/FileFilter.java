package com.figure.op.videoplay.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.File;

/**
 * @author lizhijie
 * @version 1.0
 * @description:TODO
 * @date 2023/9/20 16:21
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class FileFilter implements java.io.FileFilter {
    private String startime;
    private String endTime;
    @Override
    public boolean accept(File pathname) {
        String filename = pathname.getName().split(".mp4")[0];
        if (filename.compareTo(startime)>=0&&filename.compareTo(endTime)<=0){
            return true;
        }
        return false;
    }
}
