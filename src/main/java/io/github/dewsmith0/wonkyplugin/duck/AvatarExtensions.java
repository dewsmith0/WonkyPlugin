package io.github.dewsmith0.wonkyplugin.duck;

import io.github.dewsmith0.wonkyplugin.lua.WonkyWebSocket;

import java.util.ArrayList;

public interface AvatarExtensions {
    ArrayList<WonkyWebSocket> wonky$getOpenWebSockets();
}
