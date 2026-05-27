<img alt = "Survival Fly Logo" src="https://cdn.modrinth.com/data/cached_images/e2b9551da9335534161ce8effafb9c68e8e12630_0.webp">

---

# Adds a <i>/flight</i> command for survival players!
<img alt = "Client-side, server optional" width = 350 src="https://img.shields.io/badge/Side-Server_(Client_Optional)-05A358?style=for-the-badge&logo=serverfault">

---

# Flight (and */flight*)
<ul>
    <li>Allows the player to <code>fly in survival mode.</code></li>
    <li>By default, <code><i>anyone</i></code> can use this command.</li>
    <li><b>Command Usages:</b> <code>/flight</code>, <code>/flight <i>enable/disable 'target(s)'</i></code></li>
    <li>Only server operators can toggle flight for others players.</li>
    <li><code>This feature is available in all versions!</code></li>
    <li>You can press <code>CTRL + ALT + F</code> to toggle flight <code>(exclusive to newer versions, 1.3 and above).</code></li>
    <br>
<p>
    <img alt = "/flight Command" width="600" src="https://github.com/Dillon8775/ImageGIFs/blob/survival-fly/NEW%20gifs/flight%20command%20small%20lol%202.gif?raw=true">
</p>
</ul>

---

# Flight Speed
<ul>
    <li>Changes the <code>player's flight speed.</code></li>
    <li><b>Command Usages:</b> <code>/flightspeed <i>set speed% `target(s)`</i></code>, <code>/flightspeed <i>get `target</i></code>, <code>/flightspeed <i>reset `target(s)`</i></code></li>
    <li>You can also use <code>ALT + SCROLL</code> to change flight speed, and press <code>B</code> to reset your flight speed <code>(client-side installation required).</code></li>
    <li>Only server operators can change the flight speed for other players.</li>
    <li><code>Exclusive to version 1.2 and above.</code></li>
    <br>
<p>
    <img alt = "/flightspeed Command" width="600" src="https://github.com/Dillon8775/ImageGIFs/blob/survival-fly/NEW%20gifs/flightspeed%20command%20small.gif?raw=true">
</p>
</ul>

---

# Flight Status
<ul>
    <li>Shows whether the target player has flying abilities.</li>
    <li><b>Command Usage:</b> <code>/flightstatus <i>`target`</i></code></li>
    <li>Only server operators can view the flight status of other players.</li>
    <li><code>Exclusive to version 1.1 and above.</code></li>
    <br>
<p>
    <img alt = "/flightstatus Command" width="600" src="https://github.com/Dillon8775/ImageGIFs/blob/survival-fly/NEW%20gifs/flightstatus%20command%20small.gif?raw=true">
</p>
</ul>

---

# Crouch Flight
<ul>
    <li>Allows the player to <code>crouch in-place while flying.</code></li>
    <li>Hold <code>CTRL + SHIFT</code> to use crouch flight.</li>
    <li><i>Anyone</i> can use this feature, if they enable it client-side in their options.</li>
    <li><code>Exclusive to newer versions (1.3.1 and above).</code></li>
    <br>
<p>
    <img alt = "Crouch Flight" width="600" src="https://github.com/Dillon8775/ImageGIFs/blob/survival-fly/NEW%20gifs/crouch%20flight.gif?raw=true">
</p>
</ul>

---

# Friendly Flight
<ul>
    <li><code>Temporarily disables player flight abilities when entering combat, or being attacked.</code></li>
    <li>Flight will be disabled for <code>20 seconds,</code> for each player involved in combat.</li>
    <li>You can choose if flight should be disabled from <code>player combat</code> <i>and</i> <code>mob combat</code>, or <code>player combat only</code>.</li>
    <li><code>For singleplayer users,</code> set the <code>"Friendly Flight"</code> option to either <code>Players and Mobs</code>, or <code>Players only</code>.</li>
    <li><code>For server owners,</code> use the following command: <code>/friendlyflight <i>off / players_and_mobs / players_only</i></code></li>
    <li><code>Exclusive to newer versions (1.3 and above).</code></li>
    <br>
<p>
    <img alt = "Friendly Flight" width="600" src="https://github.com/Dillon8775/ImageGIFs/blob/survival-fly/NEW%20gifs/friendly%20flight.gif?raw=true">
</p>
</ul>

---

# Elytra Flight
<ul>
    <li>Requires players to wear an elytra to use <code>flying abilities.</code></li>
    <li>Elytra durability will decrement as the player uses their <code>flying abilities.</code></li>
    <li><code>The faster the player's flight speed, the more elytra durability will wear down.</code></li>
    <li><code>Exclusive to newer versions (1.3 and above).</code></li>
    <br>
<p>
    <img alt = "Elytra Flight" width="600" src="https://github.com/Dillon8775/ImageGIFs/blob/survival-fly/NEW%20gifs/elytra%20flight.gif?raw=true">
</p>
</ul>

---

# Flight Exhaustion
<ul>
    <li>Makes players lose hunger when using <code>flying abilities.</code></li>
    <li><code>The faster the player's flight speed, the more hunger they lose.</code></li>
    <li><code>Exclusive to newer versions (1.3 and above).</code></li>
    <br>
<p>
    <img alt = "Flight Exhaustion" width="600" src="https://github.com/Dillon8775/ImageGIFs/blob/survival-fly/NEW%20gifs/flight%20exhaustion.gif?raw=true">
</p>
</ul>

---

## /permissions command
Sets the permission level in which players can use basic Survival Fly commands. These commands include:
- /flight
- /flightspeed
- /flightstatus

### Permission levels:
#### Regular (default option)
- Anyone can use commands.
#### Moderator
- Players that bypass spawn protection can use commands.
#### Gamemaster
- Players that have higher authority, such as permission to use command blocks, change gamemode, change difficulty, etc. can use comands.
#### Admin
- Only players with operator can use commands.

Note: Permission level "Regular" means that players can only use Survival Fly commands on *themselves.* Only operators can use these commands on other players.

#### (since: v1.3)

---

# LuckPerms Integration

## You *can* use [LuckPerms](https://modrinth.com/mod/luckperms) with this mod! (Fabric, NeoForged and Forge *only*)

## Only compatible on *version 1.3* of the mod or higher.

### Permission Nodes for Survival Fly
- **"survivalfly.flight"** - Allows */flight* and */flightstatus* execution on *self.*
- **"survivalfly.flight_speed"** - Allows */flightspeed* execution on *self.*

### Note: LuckPerms is *optional!* Meaning, if you *don't* want to use LuckPerms:
1. Command permissions are based on Survival Fly's built-in "permission" system, which you can view above.
2. With LuckPerms installed, permissions are determined by the permission nodes, listed above.

### Due to a [NeoForged 1.21.1 incompatibility issue w/ LuckPerms,](https://github.com/LuckPerms/LuckPerms/issues/3963) LuckPerms will *not* work with this version of Minecraft on NeoForged.

---

# For older version players (*1.21.11 [not including 1.21.1]*, and below)

### Players *should* install this mod for themselves client-side, otherwise they will not be able to read messages when executing commands.

### Commands will still work, but messages will be unreadable. This issue has been *fixed* in version 1.3 for Minecraft 26.1 *and above.*

### To change permission level for commands, locate the "survivalfly-config.json" file, and set the permission level to one of the following strings:
- **"REGULAR"** *(now called "ANYONE" on version **1.3** and above)*
- **"MODERATOR"**
- **"GAMEMASTER"**
- **"ADMIN"**
- **"OWNER"** *(no longer exists in version **1.3** and above)*

---

# Requires [Balm](https://modrinth.com/mod/balm/versions) and [YetAnotherConfigLib](https://modrinth.com/mod/yacl/versions)!

---

Created by Dillon8775. All rights reserved unless explicitly stated.