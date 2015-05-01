This plugin is an update to the original RandMan plugin made by LRFLEW. Unlike the previous version, this version currently <span style="color:red">DOESNT</span> have special support for Towny. ~~Nether support has not yet been added. Although, you can be expecting it soon.~~ Now <span style="color:green">SUPPORTS</span> Nether

The plugin basically teleports players to a random spot on command. It can only work with a map that has a world border. As of now, it uses the plugin WorldBorder. This is mainly because of the fill function that the plugin gives. I highly recommend that you use the fill function to render all the chunks, this decreases the lag that would be cause when a player gets tped to an unloaded chunk. Support for other Border plugins may be added. Please request for them in the plugin discussions page.

The plugin lets you choose the delay (in seconds) before the player is teleported. It also cancels the teleport if the player moves withing the given time delay.
If you enable World Guard support in the config, the plugin whether there is a protected region before the player is teleported. If there is one, the teleportation request is cancelled. This is useful when you dont want players to teleport into unwanted places such as minigame/pvp arenas, certain admin areas, etc. It is suggested that world guard support be turned off if most of the space in your world is taken up by regions as players might face teleport timeout often.

Please Note that this plugin <span style="color:red">CANNOT</span> work without [World Border](http://dev.bukkit.org/bukkit-plugins/worldborder/) [![Github Link](http://legitplay.net/plugins/Downloads/Images/github.jpg)](https://github.com/Brettflan/WorldBorder)

If you like my work, please consider donating, I would greatly appreciate it. [![Donation](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=vik1395lp%40gmail%2ecom&lc=US&item_name=Spigot%20Plugins&item_number=LegitPlay%2enet%20Plugin%20Dev&no_note=0&currency_code=USD&bn=PP%2dDonationsBF%3abtn_donateCC_LG%2egif%3aNonHostedGuest) 

###Permissions
randman.use - Players with this permission are allowed to use the plugin's commands.
randman.admin - Players with this permission bypass the teleport delay.

###Commands
/rand

/randman

/random

All commands tp the player to a random area withing the World Border.

###Config File
```java
World Guard Hook: True
# Hooking into world guard means that the plugin doesnt teleport the player into a world guard region. Disable this if most of the map is occupied by regions as it will time out alot. Type true to hook into WorldGuard.
Teleport Timer: 3
# The time limit before the player is teleported.
Nether Support: True
# This field lets you allow or deny players to use the /rand command in the nether. Type True to allow Nether Support.
```

###Old Version

The last update by LRFLEW was 1.o.2

The Plugin can be found [here](http://legitplay.net/plugins/Downloads/Bukkit/RandMan/1.0.2/RandMan.jar) [![Github Link](http://legitplay.net/plugins/Downloads/Images/github.jpg)](https://github.com/LRFLEW/RandMan) 

This plugin is licensed under CC Attribution-ShareAlike 4.0 Internationa
[![Creative Commons License](http://i.creativecommons.org/l/by-nc-nd/3.0/88x31.png)](http://creativecommons.org/licenses/by-sa/4.0/deed.en_US)

In very basic terms, Do whatever you want with the code of this plugin, as long as you give credits to the author and/or the plugin itself.

Plugin made by a member of

[![LegitPlay.net](http://legitplay.net/images/logo.gif)](http://legitplay.net/)


[![Github Link](http://legitplay.net/plugins/Downloads/Images/github.jpg)](https://github.com/vik1395/RandMan) - Repository Link

Written on [Dillinger](http://dillinger.io/)