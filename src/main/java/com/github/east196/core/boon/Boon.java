package com.github.east196.core.boon;

import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.format.FastDateFormat;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.io.resource.FileResource;
import cn.hutool.core.io.resource.UrlResource;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.URLUtil;
import com.google.common.base.*;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import com.google.common.primitives.Primitives;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Array;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class Boon {

    public static int nThreads() {
        int numberOfCore = Runtime.getRuntime().availableProcessors();
        double blockingCoefficient = 0.9;
        int poolSize = (int) (numberOfCore / (1 - blockingCoefficient));
        return poolSize;
    }

    public static void sleep(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String uuid32() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String areaCode(String prefix) {
        return prefix + UUID.randomUUID().toString().replace("-", "");
    }

    public static <F, T> List<T> map(List<F> fromList, Function<? super F, ? extends T> function) {
        return Lists.transform(fromList, function);
    }

    public static <T> List<T> filter(List<T> unfiltered, Predicate<? super T> predicate) {
        return Lists.newArrayList(Iterables.filter(unfiltered, predicate));
    }

    public static <T> String toString(List<T> list) {
        return isEmpty(list) ? "" : Joiner.on(',').join(list);
    }

    public static List<String> toList(String strs) {
        return isEmpty(strs) ? Lists.newArrayList() : Splitter.on(',').splitToList(strs);
    }

    public static Date praseDate(String dateOrDateTime) {
        try {
            return DateUtil.parse(dateOrDateTime);
        } catch (DateException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static final FastDateFormat SHORT_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd");
    public static final FastDateFormat LONG_DATE_FORMAT = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");
    public static final FastDateFormat YYYYMMDDHHMMSS_FORMAT = FastDateFormat.getInstance("yyyyMMddHHmmss");
    public static final FastDateFormat YYYYMMDD_FORMAT = FastDateFormat.getInstance("yyyyMMdd");

    public static String shortDate(Date date) {
        return SHORT_DATE_FORMAT.format(date);
    }

    public static String shortDate(long millis) {
        return SHORT_DATE_FORMAT.format(millis);
    }

    public static String longDate(Date date) {
        return LONG_DATE_FORMAT.format(date);
    }

    public static String longDate(long millis) {
        return LONG_DATE_FORMAT.format(millis);
    }

    public static boolean notBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if ((cs == null) || ((strLen = cs.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (Character.isWhitespace(cs.charAt(i)) == false) {
                return false;
            }
        }
        return true;
    }

    public static boolean notEmpty(Object obj) {
        return length(obj) != 0;
    }

    public static boolean isEmpty(Object obj) {
        return isNullOrEmpty(obj);
    }

    public static boolean isNullOrEmpty(Object obj) {
        return length(obj) == 0;
    }

    public static int length(Object obj) {
        if (obj == null) {
            return 0;
        } else if (obj instanceof CharSequence) {
            return ((CharSequence) obj).length();
        } else if (obj instanceof Collection) {
            return ((Collection<?>) obj).size();
        } else if (obj instanceof Map) {
            return ((Map<?, ?>) obj).size();
        } else if (obj.getClass().isArray()) {
            return Array.getLength(obj);
        } else {
            return 1;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] toArray(Collection<T> list, Class<T> type) {
        return list.toArray((T[]) Array.newInstance(type, list.size()));
    }

    public static String md5(String text) {
        HashCode hashCode = Hashing.md5().hashString(text, Charsets.UTF_8);
        return hashCode.toString();
    }

    public static String md5(File file) {
        try {
            HashCode hashCode = Files.hash(file, Hashing.md5());
            return hashCode.toString();
        } catch (Throwable throwable) {
            Throwables.throwIfUnchecked(throwable);
            return "";
        }
    }

    public static void copy(File from, File to) {
        try {
            Files.copy(from, to);
        } catch (Throwable throwable) {
            Throwables.throwIfUnchecked(throwable);
        }
    }

    public static void copy(InputStream from, File to) {
        try {
            Files.write(ByteStreams.toByteArray(from), to);
        } catch (Throwable throwable) {
            Throwables.throwIfUnchecked(throwable);
        }
    }

    public static String toString(File file) {
        try {
            return Files.toString(file, Charsets.UTF_8);
        } catch (Throwable throwable) {
            Throwables.throwIfUnchecked(throwable);
            return "";
        }
    }

    public static String getFileExtension(String filename) {
        if (filename.endsWith(".tar.gz")) {
            return "tar.gz";
        }
        return Files.getFileExtension(filename);
    }

    public static String getNameWithoutExtension(String filename) {
        String nameWithoutExtension = Files.getNameWithoutExtension(filename);
        if (filename.endsWith(".tar.gz")) {
            nameWithoutExtension += ".tar";
        }
        return nameWithoutExtension;
    }

    public static InputStream getResourceStream(String resource) throws IOException {
        return Resources.getResource(resource).openStream();
    }

    public static Duration timeDuration(Integer seconds) {
        if (seconds == null || seconds == 0L) {
            return Duration.ZERO;
        }
        return Duration.ofSeconds(seconds.longValue());
    }

    /**
     * 从毫秒数获取时间间隔
     *
     * @param mills
     * @return
     */
    public static Duration timeDuration(Long mills) {
        if (mills == null || mills == 0L) {
            return Duration.ZERO;
        }
        return Duration.ofMillis(mills);
    }

    public static Duration timeDuration(Date startTime, Date endTime) {
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            return Duration.ZERO;
        }
        return Duration.ofMillis(endTime.getTime() - startTime.getTime());
    }

    public static Long timeDurationToMin(Date startTime, Date endTime) {
        if (startTime == null || endTime == null || startTime.after(endTime)) {
            return Duration.ZERO.toMinutes();
        }
        return Duration.ofMillis(endTime.getTime() - startTime.getTime()).toMinutes();
    }

    public static byte[] toByteArray(int value) {
        return new byte[]{(byte) (value >> 24), (byte) (value >> 16), (byte) (value >> 8), (byte) value};
    }

    /**
     * 判断一个对象是否是基本类型或基本类型的封装类型
     */
    public static boolean isPrimitive(Object obj) {
        return Primitives.isWrapperType(Primitives.wrap(obj.getClass()));
    }

    /**
     * 判断一个对象是否是基本类型或基本类型的封装类型或者String
     */
    public static boolean isPrimitiveOrString(Object obj) {
        return Primitives.isWrapperType(Primitives.wrap(obj.getClass())) || obj.getClass().equals(String.class);
    }

    public static void close(AutoCloseable autoCloseable) {
        IoUtil.close(autoCloseable);
    }

    public static InputStream getInputStream(String path) {
        if (StrUtil.isNotBlank(path)) {
            if (path.startsWith("http:") || path.startsWith("https:")) {
                return new UrlResource(URLUtil.url(path)).getStream();
            }
            if (path.startsWith("file:") || FileUtil.isAbsolutePath(path)) {
                return new FileResource(path).getStream();
            }
        }
        return new ClassPathResource(path).getStream();
    }

    @SneakyThrows
    public static OutputStream getOutputStream(InputStream inputStream) {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result;
    }

    @SneakyThrows
    public static String toString(String path) {
        InputStream inputStream = getInputStream(path);
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }

}
