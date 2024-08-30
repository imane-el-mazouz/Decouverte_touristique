package com.tourist.util;

import com.tourist.exception.FuncErrorException;
import lombok.experimental.UtilityClass;
import org.springframework.web.multipart.MultipartFile;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class FileUploadUtil {

    public static final long MAX_FILE_SIZE = 5 * 1024 * 1024; // 5 MB

    public static final String IMAGE_PATTERN = "([^\\s]+(\\.(?i)(jpg|jpeg|png|gif|bmp|tif|webp|svg))$)";

    public static final String DATE_FORMAT = "yyyyMMddHHmmss";

    public static final String FILE_NAME_FORMAT = "%s_%s";

    /**
     * Checks if the file extension is allowed based on the provided pattern.
     *
     * @param fileName the name of the file
     * @param pattern  the regex pattern to check against
     * @return true if the file extension matches the pattern, false otherwise
     */
    public static boolean isAllowedExtension(final String fileName, final String pattern) {
        final Matcher matcher = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE).matcher(fileName);
        boolean matches = matcher.matches();
        return matches;
    }

    /**
     * Asserts that the file is allowed based on its size and extension.
     *
     * @param file   the file to check
     * @param pattern the regex pattern for allowed file extensions
     * @throws FuncErrorException if the file size exceeds the maximum allowed size or the file type is not allowed
     */
    public static void assertAllowed(MultipartFile file, String pattern) {
        final long size = file.getSize();
        if (size > MAX_FILE_SIZE) {
            throw new FuncErrorException("Max file size is 5MB");
        }

        final String fileName = file.getOriginalFilename();
        if (!isAllowedExtension(fileName, pattern)) {
            throw new FuncErrorException("Only allowed file types are: " + pattern);
        }
    }

    /**
     * Generates a file name using the provided name and the current timestamp.
     *
     * @param name the original file name
     * @return the generated file name
     */
    public static String getFileName(final String name) {
        final DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        final String date = dateFormat.format(System.currentTimeMillis());
        return String.format(FILE_NAME_FORMAT, name, date);
    }
}
