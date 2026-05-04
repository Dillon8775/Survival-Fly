# Version 1.3.1 (Fabric 26.1, NeoForge 26.1, *and* 1.21.1 for Fabric/NeoForged/Forge):

# Toggle Flight Keybind
- Added a `keybind to toggle flight,` default = `CTRL + ALT + F`.
- `(Fabric 1.21.1 only) -` Due to Kuma key API not supporting modifiers, the key is unbounded by default.

# Crouch Flight
- `Crouch in-place while flying, without descending.`
- To use it, hold `CTRL + SHIFT.`
- Toggleable via the config.

# Safe Mode
- A new option, which is enabled by default, and `prevents players from joining servers that do not have Survival Fly installed.`
- This has been done to `prevent the confusion of players thinking that this mod is a hack or cheat.` It's not. This mod is a choice, determined by server owners/administrators, on `whether or not they want players to be able to fly in survival mode.` If the mod isn't installed, then you can be sure that the server staff `do not want players cheating by flying in survival mode.`
- Doesn't work and disabled on Forge 1.21.1.

# Dependency Changes (26.1+ *only*)
- Runs on `Minecraft 26.1.2`, not 26.1.1 or 26.1.
- *(Fabric only)* Now requires `Fabric Loader` version `0.19.2` or greater to load.
- *(NeoForged only)* Now requires `NeoForged-beta` version `26.1.2.36` or greater to load.
- Now requires `Balm` version `25.1.2.5` or greater to load.

# Bugs Fixed
- [Bug 1](https://github.com/Dillon8775/Survival-Fly/issues/10) - `Command blocks` and `non-players` `/flight`, `/flightstatus` and `/flightspeed`.
- `"Friendly Flight"` option is now disabled client-side when the player is on a server (because this option is determined by the server).