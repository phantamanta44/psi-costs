# Psio / Psi-Costs

[Psi](https://github.com/Vazkii/Psi) is overpowered. Today, we have the tools to fix that.

# Features

**Psio** (or Psi-Costs or whatever you want to call it) takes a very simple approach to balancing Psi: it makes spells actually cost something more material than time. To this end, PSI (abbr. psi energy) will no longer passively regenerate. Instead, players must carry PSI-storing items in their inventory from which PSI can be drawn.

A couple extra tweaks are added to supplement these changes. Firstly, players collecting experience points while their PSI pools aren't topped-off will cause some of the XP to be converted to PSI. Secondly, all players will have Psi fully unlocked as soon as they join the game. Both of these behaviours can be modified and disabled in the config file.

[JEI](https://github.com/mezz/JustEnoughItems) is recommended for playing with this mod. It'll make your life easier.

## PSI-Storing Items

When a player casts a spell, the cost of the spell is deducted from the PSI bar. Then, once the regeneration cooldown ends, Psio will search the player's inventory for PSI-storing items, which will then be emptied to refill the PSI bar.

Two types of PSI-storing items exist: single-use cells and rechargeable cells. Single-use cells can only be extracted from whereas rechargeable cells can, as one might guess, be recharged.

* **PSI Stimulator** - Single-use shot of PSI. Breaks upon being depleted.
* **PSI Cell** - Rechargeable PSI cell. Comes in four tiers with increasing capacity.
* **Star of PSI** - Creative-only PSI source that provides infinite PSI.

## Generating PSI

Several machines exist which can generate PSI and recharge PSI cells. Most of them rely on other mods to do this; users of Psio are recommended to install at least one such compatible mod.

* **Flux Resonator** - Generates PSI from Forge Energy.
* **Psionic Lensing Pedestal** - Generates PSI from [Botania](https://github.com/Vazkii/Botania) mana.
* **Cold Light Projector** - Generates PSI from [Astral Sorcery](https://github.com/HellFirePvP/AstralSorcery) liquid starlight.
* **Sanguine Psiogenesis Device** - Generates PSI from [Blood Magic](https://github.com/WayofTime/BloodMagic) life essence.
* **Psionic Induction Cage** - Generates PSI from [Embers](https://github.com/DaedalusGame/EmbersRekindled) ember.
* **Potentia Invertor** - Generates PSI from Thaumcraft potentia essentia.

## Spell Pieces

Psio currently adds one spell piece:

* **Trick: Draw Energy** - Draws PSI from a PSI-storing block in the world. Will also recharge PSI-storing items in the caster's inventory.

## Other Stuff

* If [Baubles](https://github.com/Azanor/Baubles) is installed, PSI cells will become trinkets. Very handy for conserving inventory space.

# Dependencies

* [libnine](https://github.com/phantamanta44/libnine) (Required)
* [Psi](https://github.com/Vazkii/Psi) (Required)
* [JEI](https://github.com/mezz/JustEnoughItems) (Optional)
* [Baubles](https://github.com/Azanor/Baubles) (Optional)
* [Botania](https://github.com/Vazkii/Botania) (Optional)
* [Astral Sorcery](https://github.com/https://github.com/HellFirePvP/AstralSorcery) (Optional)
* [Blood Magic](https://github.com/WayofTime/BloodMagic) (Optional)
* [Embers](https://github.com/DaedalusGame/EmbersRekindled) (Optional)
* Thaumcraft (Optional)

# Attributions

* Vazkii - Default "Draw Energy" sound effect is borrowed from [Botania](https://github.com/Vazkii/Botania). 
