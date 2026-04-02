# Version 1.3 (Fabric 26.1, NeoForge 26.1):

# === New Balancing Features and Commands! ===

---

# Balm Dependency
As a part of this update, [Balm](https://modrinth.com/mod/balm) is now *required* in order to use this mod. This is to make developing on multiple Minecraft versions *and* mod loaders, way easier. It's available on *all mod platforms,* so you don't have to worry about compatibility issues.

---

## Elytra Flight *(/elytraflight)*
- A new feature/command, which requires all player's to be wearing an elytra to use /flight!
- If the player does not have an elytra equipped, they cannot use /flight.
- As the player flies with their elytra, the durability will decrement, and decrement faster the higher the player's flight speed.
- For operators, use */elytraflight* to toggle the feature for your server. For singleplayer users, you must enable it via the config.
- **OFF** by default.

## Flight Exhaustion *(/flightexhaustion)*
- Another new feature/command, which makes players hungry when they use the flight ability!
- As the player flies, they will lose hunger, and lose even more depending on the player's flight speed.
- For operators, use */flightexhaustion* to toggle the feature for your server. For singleplayer users, you must enable it via the config.
- **OFF** by default.

## Friendly Flight *(/friendlyflight)*
- The final new feature/command, which instantly disables players' flying abilities if they take damage!
- If the player is flying (or *can* fly) and takes damage, their flight is disabled for 20 seconds. After the 20 seconds are up, their flying abilities are automatically re-enabled again.
- If the player doesn't have flying abilities to begin with, nothing happens.
- For operators, use */friendlyflight* to toggle this feature for your server. For singleplayer users, you must enable it via the config.
- **OFF** by default.

---

# New Commands and Command Changes
- Player's without operator can no longer use */flightstatus* or */flightspeed* on other players. They *can* still use it on themselves, depending on the "Permission Level" option.
- All player's can still use the /flight command, depending on the "Permission Level" option.
- All new commands for features listed above have been added, which only operators can execute.
- Added *"/permissions"*, command which allows server/world owners to change the permission level at which players can use /flight command. Only the *owner* of the world/server can execute commands. Operators cannot execute it.
- Added *"/survivalfly"*, which displays all of survival fly's commands, and how to use them. Anyone can execute this command, regardless of permission level.

## Message Changes & Support for Server Players
- All translations are now "literal" texts stored server-side, meaning players do *not* need the mod installed client-side in order to read messages when executing commands.
    - The one downside to this is that creating translations for other languages is not currently possible.

## Option Changes
- Added the option to display the Survival Fly config button at the bottom left or right hand side of the pause screen.
- Renamed "Permission Level" option to "Permissions".
- Removed "Owner" permission level. "Admin" is now the highest permission level.
- Removed "Change Flight Speed On Rule" option (anyone can change their flight speed using ALT if they have flying abilities).
- Server-side options are grayed out for all player's config screens if they are playing on a server.

---

## Other Changes
- (Fabric only) The mod now requires Fabric Loader version **0.18.5.**
- Added config support for NeoForge.
- Updated commands for new features.
- Updated lang and text.
- Tweaks and optimizations.

## NeoForge BugFix
- Fixed bug where joining a NeoForge server that doesn't have the Survival Fly mod installed will not allow connection.

## Technical Change
- Player "flight" values are no longer stored in memory, they are written to the player's data, meaning improved stability when relogging/restarting world (or server) and keeping flight status correct for all players.