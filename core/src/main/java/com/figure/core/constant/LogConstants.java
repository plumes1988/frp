package com.figure.core.constant;

public class LogConstants {

    //最低级别的日志，用于追踪程序的内部运行情况。包含详细的调试信息和变量值，通常用于排查问题和调试
    public static String TRACE = "TRACE";

    //用于调试目的的详细日志信息，可用于记录程序的内部状态、变量值和流程信息。在生产环境中一般不建议启用该等级的日志，因为会产生大量的日志输出
    public static String DEBUG = "DEBUG";

    //用于记录程序正常操作的重要信息，例如应用程序的启动、关键事件和状态变化等。INFO级别的日志通常用于向管理员和操作人员传达有用的运行时信息
    public static String INFO = "INFO";

    //用于记录可能会导致问题或异常的潜在问题的警告信息。警告信息可能表明应用程序遇到了一些不正常的情况，但不会导致应用程序的停止或严重错误
    public static String WARN = "WARN";

    //用于记录错误和异常信息。ERROR级别的日志通常表示应用程序遇到了一个错误或异常情况，并且无法继续正常运行。这些日志需要及时关注和处理
    public static String ERROR = "ERROR";

    //最高级别的日志，用于记录严重错误和致命异常。FATAL级别的日志通常表示应用程序遇到了一个无法恢复的错误，导致应用程序终止运行或崩溃
    public static String FATAL = "FATAL";

}
