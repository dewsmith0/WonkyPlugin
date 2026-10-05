package io.github.dewsmith0.wonkyplugin.helper;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.figuramc.figura.FiguraMod;
import org.figuramc.figura.config.Configs;
import org.figuramc.figura.utils.ColorUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;

// The methods in this file are taken from Figura, licensed under Polyform Noncommercial License 1.0.0. (slightly modified)
// Trust me, it's way cleaner to copy this code than doing it the way I first tried it.
// (Which, by the way, had 3 unchecked operations and felt like it could break if I looked at it wrong. That's what 3 AM code gets you.)

public class NetLogger {
    private static FileOutputStream logFileOutputStream;

    public static void log(String source, String owner, Component text) {
        // 0 - FILE, 1 - FILE + LOGGER, 2 - FILE + LOGGER + CHAT, 3 - NONE
        int log = Configs.LOG_NETWORKING.value;
        if (log == 3) return;
        MutableComponent finalText =
                Component.literal("[networking:%s:%s] ".formatted(source.toLowerCase(Locale.US),owner))
                        .withStyle(ColorUtils.Colors.LUA_PING.style)
                        .append(text.copy().withStyle(ChatFormatting.WHITE));
        String logTextString = finalText.getString();
        switch (log) {
            case 2 -> FiguraMod.sendChatMessage(finalText);
            case 1 -> FiguraMod.LOGGER.info(logTextString);
        }
        if (logFileOutputStream == null) prepareLogStream();
        try {
            LocalTime t = LocalTime.now();
            writeToLogStream("[%02d:%02d:%02d] [INFO] %s\n".formatted(t.getHour(), t.getMinute(),
                    t.getSecond(), finalText.getString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void error(String source, String owner, Component text) {
        // 0 - FILE, 1 - FILE + LOGGER, 2 - FILE + LOGGER + CHAT, 3 - NONE
        int log = Configs.LOG_NETWORKING.value;
        if (log == 3) return;
        MutableComponent finalText =
                Component.literal("[networking:%s:%s] ".formatted(source.toLowerCase(Locale.US),owner))
                        .withStyle(ColorUtils.Colors.LUA_ERROR.style)
                        .append(text.copy().withStyle(ChatFormatting.WHITE));
        String logTextString = finalText.getString();
        switch (log) {
            case 2 -> FiguraMod.sendChatMessage(finalText);
            case 1 -> FiguraMod.LOGGER.error(logTextString);
        }
        if (logFileOutputStream == null) prepareLogStream();
        try {
            LocalTime t = LocalTime.now();
            writeToLogStream("[%02d:%02d:%02d] [ERROR] %s\n".formatted(t.getHour(), t.getMinute(),
                    t.getSecond(), finalText.getString()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeToLogStream(String s) throws IOException {
        logFileOutputStream.write(s.getBytes(StandardCharsets.UTF_8));
        logFileOutputStream.flush();
    }

    private static void prepareLogStream() {
        try {
            Path p = FiguraMod.getFiguraDirectory().resolve("logs");
            File folder = p.toFile();
            folder.mkdirs();
            LocalDate d = LocalDate.now();
            File logFile = p.resolve(
                    String.format("ws-%d-%02d-%02d.log", d.getYear(), d.getMonthValue(), d.getDayOfMonth())
            ).toFile();
            logFileOutputStream = new FileOutputStream(logFile, true);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
