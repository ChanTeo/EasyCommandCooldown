![Banner](https://abload.de/img/easycommandcooldown_b7gkum.png)
Minecraft Spigot Plugin to add cooldowns to any command.

# Overview
Command Cooldown was made to give Serverowners who rely on third Party Plugins the opportunity
to add cooldowns to every command.

The Plugin is extremely versatile and completely customizable.
It relies on an easy to understand configuration file.

## Information



### Commands

```/easycc reload``` The only Command. Reload the configuration on runtime for easy file-editing and updating.


### Permission
```easycc.admin``` the only hardcoded permission. Only used for the ```/reload``` command

### Configuration
The configuration file has comments for each option to ensure a fast and easy setup.
If you want to restore the defaults, simply delete the configuration and use the ```/reload``` command or restart your Server

```yaml
#----------------------------------------
#Easy Command Cooldown configuration file.
#----------------------------------------
#
general:
  # don't change. This is to verify if the config is missing something.
  version: 1.0
  # This sends usage Statistics to bstats.org. This Helps me improving the Plugin.
  # you can disable it any time.
  useStatistics: true;

# Add as many "commands" as you like. The identifier can be anything you like but has to be unique
command:
  # Identifier
  1:
    # This is the command that is checked
    execution: '/easycc'
    # the permission to bypass the cooldown
    bypass: 'easycc.admin'
    # You can add as many cooldowns as you like (no doubles) Cooldown in seconds.
    # 1 min = 60 sec, 1 hour = 3600 sec, 1 Day = 86400
    # If someone has permission for multiple cooldowns, the lowest cooldown is taken
    # If someone has no permission for any of the cooldown, the highest is taken.
    # If you are using Luckperms: enter "group.groupname" to allow a group to use the command.
    cooldowns:
      15: 'easycc.default'
      10: 'easycc.donor'
      5: 'easycc.donor2'
      0: 'easycc.admin'

# The messages which appear ingame. Change to you liking. (Use 2 single quotes ('') to escape a single quote)
# Minecraft Formatting Codes are supported.
message:
  # Prefix of the messages, if you don't want to use a prefix leave single quotes ('')
  plugin_prefix: '&7&l[&2&lEasy&f&lCommand&7&l] &f'
  no-permission: '&7You don''t have permission to use this command!'
  # You can use {time} to show in seconds or %timeFormatted% for time in "hrs, min, sec"
  command-on-cooldown: '&7you have to wait &2%timeFormatted% &7to use %command% again'

```
### Good to know
The plugin works well with every permission plugin. You can enter your own permission or an existing permission for each cooldown and
can add as many cooldowns per command as you like. This makes the plugin extremely easy to setup as you don't have to mess arround with
your existing permissions and can just simply enter an existing one.

