# Ultimate ItemActions

**Ultimate ItemActions** is a Minecraft plugin that allows you to create fully customized and interactive items.  
When players interact with these items, predefined actions are executed automatically.

The plugin is designed to be **simple to configure**, **flexible**, and **ideal for custom servers**, events, minigames, and reward systems.

---

## Commands

Permission base: `ultimateitemactions.admin`

### Main command
```text
/ultimateitemactions
/uitemactions
/uiactions
```

### Subcommands

- `/ultimateitemactions create <item-key>`  
  Permission: `ultimateitemactions.admin.create`  
  Opens a GUI to create and configure a new item.

- `/ultimateitemactions edit <item-key>`  
  Permission: `ultimateitemactions.admin.edit`  
  Opens a GUI to edit an existing item.

- `/ultimateitemactions remove <item-key>`  
  Permission: `ultimateitemactions.admin.remove`  
  Removes an existing item.

- `/ultimateitemactions give (target) <item-key> [amount]`  
  Permission: `ultimateitemactions.admin.give`  
  Gives the specified item to a player.

---

## Item Editor GUI

The **Create** and **Edit** commands open an interactive menu where you can configure:

- Display Name
- Description (Lore)
- Actions
- Icon (Material)
- Icon Durability / Data Value

All changes are applied visually and saved automatically.

---

## Supported Actions

- `sendmessage:<message>`  
  Sends a chat message to the player.

- `play:<sound-name>`  
  Plays a sound to the player.

- `title:<title>;<subtitle>;<fadeIn>;<stay>;<fadeOut>`  
  Sends a title and subtitle to the player.

- `console:<command>`  
  Executes a command as the console.  
  You can use `{player}` as a placeholder for the player's name.

---

## Configuration Example

```yaml
# Unique ID of the interactive item
test:

  # Item material (Bukkit/Spigot Material)
  icon: INK_SACK

  # Data value / durability (legacy versions)
  durability: 14

  # Display name (supports & color codes)
  display_name: "Test"

  # Item lore
  description:
    - "This is only the test"
    - ""
    - "Click to execute."

  # Actions executed on interaction
  actions:
    - "sendmessage:Hi :)"
    - "title:&aHi people!;&eHow are you?;10;20;10"
    - "console:give {player} diamond 1"
    - "play:LEVEL_UP"
```

---

## Notes

- Color codes using `&` are supported.
- Actions are executed **in the order they are defined**.
- Sounds must match the server version's `Sound` enum.
- Designed to support legacy versions such as **1.8.x**.

---

## License

This project is provided as-is.  
You are free to modify and use it on your server.