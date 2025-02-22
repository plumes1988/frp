package com.figure.core.util.text;

/**
 * <p>
 * 驼峰转换器（操作字节数组，性能较高）
 * </p>
 * ASCII表
 * Dec  Char                         Dec  Char     Dec  Char     Dec  Char
 * ---------                         ---------     ---------     ----------
 * 0  NUL (null)                      32  SPACE     64  @         96  `
 * 1  SOH (start of heading)          33  !         65  A         97  a
 * 2  STX (start of text)             34  "         66  B         98  b
 * 3  ETX (end of text)               35  #         67  C         99  c
 * 4  EOT (end of transmission)       36  $         68  D        100  d
 * 5  ENQ (enquiry)                   37  %         69  E        101  e
 * 6  ACK (acknowledge)               38  &         70  F        102  f
 * 7  BEL (bell)                      39  '         71  G        103  g
 * 8  BS  (backspace)                 40  (         72  H        104  h
 * 9  TAB (horizontal tab)            41  )         73  I        105  i
 * 10  LF  (NL line feed, new line)   42  *         74  J        106  j
 * 11  VT  (vertical tab)             43  +         75  K        107  k
 * 12  FF  (NP form feed, new page)   44  ,         76  L        108  l
 * 13  CR  (carriage return)          45  -         77  M        109  m
 * 14  SO  (shift out)                46  .         78  N        110  n
 * 15  SI  (shift in)                 47  /         79  O        111  o
 * 16  DLE (data link escape)         48  0         80  P        112  p
 * 17  DC1 (device control 1)         49  1         81  Q        113  q
 * 18  DC2 (device control 2)         50  2         82  R        114  r
 * 19  DC3 (device control 3)         51  3         83  S        115  s
 * 20  DC4 (device control 4)         52  4         84  T        116  t
 * 21  NAK (negative acknowledge)     53  5         85  U        117  u
 * 22  SYN (synchronous idle)         54  6         86  V        118  v
 * 23  ETB (end of trans. block)      55  7         87  W        119  w
 * 24  CAN (cancel)                   56  8         88  X        120  x
 * 25  EM  (end of medium)            57  9         89  Y        121  y
 * 26  SUB (substitute)               58  :         90  Z        122  z
 * 27  ESC (escape)                   59  ;         91  [        123  {
 * 28  FS  (file separator)           60  <         92  \        124  |
 * 29  GS  (group separator)          61  =         93  ]        125  }
 * 30  RS  (record separator)         62  >         94  ^        126  ~
 * 31  US  (unit separator)           63  ?         95  _        127  DEL
 *
 * @author feather
 * @date 2021-01-12 9:50
 */
public class HumpConverter {

    /** 下划线对应的ASCII */
    private static final byte ASCII_UNDER_LINE = 95;
    /** 小写字母a的ASCII */
    private static final byte ASCII_LOWER_A = 97;
    /** 小写字母z的ASCII */
    private static final byte ASCII_LOWER_Z = 122;
    /** 大写字母A的ASCII */
    private static final byte ASCII_UPPER_A = 65;
    /** 大写字母Z的ASCII */
    private static final byte ASCII_UPPER_Z = 90;
    /** 字母a和A的ASCII差距(a-A的值) */
    private static final byte ASCII_LOWER_UPPER_DIFFERENCE = ASCII_LOWER_A - ASCII_UPPER_A;

    /**
     * 将参数b转换为大写字母,小写字母ASCII范围(97~122)
     * 1. 判断参数是否为小写字母
     * 2. 将小写字母转换为大写字母(减去32)
     */
    private static byte toUpper(byte b) {
        return (b >= ASCII_LOWER_A && b <= ASCII_LOWER_Z) ? (byte) (b - ASCII_LOWER_UPPER_DIFFERENCE) : b;
    }

    /**
     * 将参数b转换为小写字母,大写字母ASCII范围(65~90)
     * 1. 判断参数是否为大写字母
     * 2. 将大写字母转换为小写字母(加上32)
     */
    private static byte toLower(byte b) {
        return (b >= ASCII_UPPER_A && b <= ASCII_UPPER_Z) ? (byte) (b + ASCII_LOWER_UPPER_DIFFERENCE) : b;
    }

    /**
     * 将下划线命名转换成驼峰式命名
     * <p>
     * 1. 新建一个数组，并用实例i记录当前操作元素的下标
     * 2. 找到`_`符号的ASCII码(95)对应的下标并跳过
     * 3. 将下划线下标的下一个元素转换为大写字母, 其他转换为小写字母
     * 4. 转化为字符串并清除首尾空白
     * <p>
     * 例1:HELLO_WORLD -> helloWorld
     * 例2:hello_world_to_lucky_man -> helloWorldToLuckyMan
     *
     * @param column  原始字段
     * @param isClass 首字母是否大写（为true首字母大写，否则小写）
     * @return 新字段
     */
    public static String underlineToHump(String column, boolean isClass) {
        byte[] aBytes = column.trim().getBytes();
        byte[] cBytes = new byte[aBytes.length];
        for (int i = 0, j = 0; i < aBytes.length; i++, j++) {
            if (i == 0 && isClass) {
                cBytes[j] = toUpper(aBytes[i]);
            } else {
                cBytes[j] = aBytes[i] == ASCII_UNDER_LINE ? toUpper(aBytes[++i]) : toLower(aBytes[i]);
            }
        }
        return new String(cBytes).trim();
    }

    /**
     * 将驼峰式命名转换成下划线命名
     * <p>
     * 1. 新建一个数组，并用实例j记录当前操作元素的下标
     * 2. 找到大写字母ASCII码(65~90)对应的下标
     * 3. 将大写字母转换为小写字母，并将下划线放到当前下标的下一个元素
     * 4. 转化为字符串并清除首尾空白
     * </p>
     * 例1:helloWorld -> hello_world
     * 例2:HelloWorldToLuckyMan -> hello_world_to_lucky_man
     *
     * @param column 原始字段
     * @return 新字段
     */
    public static String humpToUnderline(String column) {
        byte[] aBytes = column.trim().getBytes();
        byte[] cBytes = new byte[aBytes.length * 2];
        for (int i = 0, j = 0; i < aBytes.length; i++, j++) {
            if (i != 0 && aBytes[i] >= ASCII_UPPER_A && aBytes[i] <= ASCII_UPPER_Z) {
                cBytes[j++] = ASCII_UNDER_LINE;
            }
            cBytes[j] = toLower(aBytes[i]);
        }
        return new String(cBytes).trim();
    }

}
