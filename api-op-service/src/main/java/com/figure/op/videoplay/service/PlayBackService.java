package com.figure.op.videoplay.service;

import com.figure.op.cameramanager.domain.CameraInfo;
import com.figure.op.common.domain.R;
import com.figure.op.videoplay.domain.vo.HistoryThermometry;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface PlayBackService {

    List<HistoryThermometry> historyThermometry(Integer cameramaId, String startTime, String endTime);

    void getVidedo(Integer cameramaId, String startTime, String endTime, HttpServletResponse response);

    boolean stopAndDeleteDir(Integer cameramaId);
    boolean updateVideoTape(CameraInfo cameraInfo);
    boolean addVideoTape(CameraInfo cameraInfo);
    R getM3u8FIle(Integer cameramaId, String startTime, String endTime, HttpServletResponse response);
}
