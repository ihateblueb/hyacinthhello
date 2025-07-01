# HyacinthHello

A simple plugin that lets players set their own messages that send when they join, leave, or die.

Messages can be set from `/joinmsg`, `/leavemsg`, and `/deathmsg` and are stored in the plugin directory under `PlayerDatabase`.

## Commands

Messages are set with commands.
- `/joinmsg [message]`
- `/leavemsg [message]`
- `/deathmsg [message]`

You can clear a message by passing a blank value.

## Configuration

The configuration allows you to modify how the plugin looks. Here's an example:

```yaml
enabled: true # Whether to enable the plugin
prefix: "" # Prefix before command responses
wrapper-left: "&e&o" # Prefix for messages
wrapper-right: "" # Suffix for messages
maximum-message-length: 60 # Checked when player sets message
```

## Permissions

- `hyacinthhello.use` to use any commands
- `hyacinthhello.joinmsg` to set and have join messages sent
- `hyacinthhello.leavemsg` to set and have leave messages sent
- `hyacinthhello.deathmsg` to set and have death messages sent
- `hyacinthhello.color` to use color in messages

## Placeholders

Each message type has its own placeholder. Placeholders will not include the wrappers set in the config.
- `%hyacinthhello_join%`
- `%hyacinthhello_leave%`
- `%hyacinthhello_death%`

Placeholders will also have color and formatting in them if the player has permission. You can exclude that:
- `%hyacinthhello_join-clean%`
- `%hyacinthhello_leave-clean%`
- `%hyacinthhello_death-clean%`

You can also pass a username to get a specific player's message.
- `%hyacinthhello_join_[username]%`