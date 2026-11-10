# Version 1.3.9
- Mod now uses [YACL](https://modrinth.com/mod/yacl) (YetAnotherConfigLib) again, this time with more server configurability.
- Administrators can now modify the mod's server settings via the Survival Fly configuration screen.
    - If the player is in a singleplayer world, any setting modified in the screen will change the player's local config (they do not need admin permissions in their world for this).
    - If a player is on a server, any setting modified will modify that server's config, if the player has administrator permissions (if they don't have permission, the option will be grayed out and they will be unable to change the options).
- Client-config now only registers on the client.
- Renamed the Flight Speed Modification option to Allow Players to Change Flight Speed.
- Added a keybind to open the mod's main menu (Tab + S).
- Fixed issue where some events are not registered on fabric.
- Removed the Toggle Flight keybind.