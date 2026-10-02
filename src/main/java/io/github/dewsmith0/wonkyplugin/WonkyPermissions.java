package io.github.dewsmith0.wonkyplugin;

import org.figuramc.figura.permissions.Permissions;

import java.util.Collection;
import java.util.List;

public class WonkyPermissions {
    public static Permissions PRINT_JSON = new Permissions("PRINT_JSON", 0, 1, 1, 1, 1);
    public static final Collection<Permissions> PERMISSIONS = List.of(PRINT_JSON);
}
