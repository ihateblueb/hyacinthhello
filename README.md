# HyacinthHello

Simple plugin for players to set their own custom join, leave, and death messages.

Messages can be set from `/joinmsg`, `/leavemsg`, and `/deathmsg` and are stored in the plugin directory under `PlayerDatabase`.

See a demo at https://www.youtube.com/watch?v=L6Li0QnvPis.

## Commands

Messages are set with commands. You can clear a message by passing a blank value.
- `/joinmsg [message]`
- `/leavemsg [message]`
- `/deathmsg [message]`

Moderators can override messages of other users if they have the `hyacinthhello.mod` permission node.
- `/hh mod joinmsg [user] [message]`
- `/hh mod leavemsg [user] [message]`
- `/hh mod deathmsg [user] [message]`

## Configuration

The configuration allows you to modify how the plugin looks. Here's an example:

```yaml
enabled: true # Whether to enable the plugin
proxy-mode: false # Whether to enable Velocity support (see below)
proxy-redis: # Ignore if above is false
  address: 0.0.0.0 # Your redis address, this is likely fine
  port: 6379 # Your redis port, this is likely fine
  channel: hyacinthhello # Leave default, unless you have multiple proxies with HyacinthHello
  ssl: false # Leave blank unless you know you have to change this
  pass: # Leave blank unless you know what to put here
database:
  type: yaml # Default is yaml. Other options are mysql or postgres
  # If you chose yaml, you can ignore the rest of the database config
  host: 0.0.0.0
  port: 3306 # Default mysql is 3306, postgres is 5432
  db: hyacinthhello # Create this
  user: # Fill this in
  pass: # Fill this in
economy:
  enabled: false # Whether to charge for changing messages
  type: 'vault' # Options are vault or playerpoints
  cost: # PlayerPoints only supports whole numbers (eg 10.0, not 10.5)
    joinmsg: 10.0
    leavemsg: 10.0
    deathmsg: 10.0
prefix: "" # Prefix before command responses
wrapper-left: "&e&o" # Prefix for messages
wrapper-right: "" # Suffix for messages
maximum-message-length: 60 # Checked when player sets message
regex-filters: # Regex filters don't need to be wrapped in /
  - "simpleexactmatch"
  - "t[a-zA-Z]st"
```

## Economy

Supports either Vault or [Rosewood Development's PlayerPoints](https://www.spigotmc.org/resources/playerpoints.80745/).

Players will not be charged for clearing their message, only setting it.

## Permissions

- `hyacinthhello.use` to use any commands
- `hyacinthhello.mod` to use mod commands
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

## Velocity

With HyacinthHello Velocity, you can have HyacinthHello's custom messages broadcasted across your entire network.

The Velocity plugin can just forward to the other servers, or take over all join, leave, and death messages across the proxy. The latter option is recommended.

If you wanted to sync player's custom messages across your proxy, you could use a single MySQL or Postgres database for all of your servers. 

Requires Redis.

On your server:

```yaml
proxy-mode: true
proxy-redis:
  address: 0.0.0.0 # Your redis address, this is likely fine
  port: 6379 # Your redis port, this is likely fine
  channel: hyacinthhello # Leave default, unless you have multiple proxies with HyacinthHello
  ssl: # Leave blank unless you know you have to change this
  pass: # Leave blank unless you know what to put here
```

On your proxy:

```yaml
# Variables:
#   {p}   player name
#   {s}   server name
#   {m}   forwarded message
redis:
  address: 0.0.0.0 # Your redis address, this is likely fine
  port: 6379 # Your redis port, this is likely fine
  prefix: hyacinthhello # Leave default, unless you have multiple proxies with HyacinthHello
  ssl: # Leave blank unless you know you have to change this
  pass: # Leave blank unless you know what to put here

override-backends: true # Overrides default and custom messages. 
join-message: '<yellow>{p} joined {s}' # Allows setting custom formatting for join
leave-message: '<yellow>{p} left {s}' # Allows setting custom formatting for leave
death-message: '<yellow>{m}' # Just wraps over the message forwarded from backend servers

wrapper-left: '&e&o'
wrapper-right: ''
```