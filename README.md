# BossBarAPI
<h3 id="w-example">Example</h3>
![BossBarAPI - Example](https://raw.githubusercontent.com/KrystianKl/BossBarAPI/master/example.png)
<h3 id="w-developers">Developers</h3>
<p>Simply add BarAPI.jar to your project build path.</p>
<h3 id="w-examples">Examples</h3>
<h5 id="w-bossbarapi-message">BossBarAPI.createBar(String message)</h5>
<p>Set a message for all players. It will remain there until the player logs off or another plugin overrides it.</p>
<h5 id="w-bossbarapi-player-message">BossBarAPI.createBar(Player player, String message)</h5>
<p>Set a message for the player. It will remain there until the player logs off or another plugin overrides it.</p>
<h5 id="w-bossbarapi-player-float">BossBarAPI.createBar(Player player, String message, float percent)</h5>
<p>Same as above except you can set the % of the BossBar. 100 shows the entire health bar, 50 shows half the health bar and etc.</p>
<h5 id="w-bossbarapi-player-seconds">BossBarAPI.setMessage(final Player player, String message, int seconds)</h5>
<p>Sets a timed message for the player. It will remain until the timer runs out (or player logs off or another plugin overrides it). The health automatically reduces based on how long the timer is.</p>
<h5 id="w-bossbarapi=player-hasbar">BossBarAPI.hasBar(Player player)</h5>
<p>Pretty self explained, returns a boolean.</p>
<h5 id="w-bossbarapi=player-setmessage">BossBarAPI.setMessage(Player player, String message)</h5>
<p>Set a message for the player. Message length must be higher then 0. Return when player has no bar.</p>
<h5 id="w-bossbarapi=player-getmessage">BossBarAPI.getMessage(Player player)</h5>
<p>Return message or null when player has no bar.</p>
<h5 id="w-bossbarapi=player-setprogress">BossBarAPI.setProgress(Player player, float progress)</h5>
<p>Set % of player BossBar. Return when player has no bar or progress number is higher then 100 or lower then 1.</p>
<h5 id="w-bossbarapi=player-getprogress">BossBarAPI.getProgress(Player player)</h5>
<p>Return % or 0 when player has no bar.</p>
<h5 id="w-bossbarapi=player-setcolor">BossBarAPI.setColor(Player player, Color color)</h5>
<p>Set a color of the BossBar for player. Return when player has no bar.</p>
<h5 id="w-bossbarapi=player-getcolor">BossBarAPI.getColor(Player player)</h5>
<p>Return color or null when player has no bar.</p>
<h5 id="w-bossbarapi=player-setstyle">BossBarAPI.setStyle(Player player, Style style)</h5>
<p>Set a style of the BossBar for player. Return when player has no bar.</p>
<h5 id="w-bossbarapi=player-getstyle">BossBarAPI.getStyle(Player player)</h5>
<p>Return style or null when player has no bar.</p>
<h5 id="w-bossbarapi=player-addflag">BossBarAPI.addFlag(Player player, Flag flag)</h5>
<p>Add flag to the BossBar for player. Return when player has no bar.</p>
<h5 id="w-bossbarapi=player-removeflag">BossBarAPI.removeFlag(Player player, Flag flag)</h5>
<p>Remove flag from the BossBar. Return when player has no bar.</p>
<h5 id="w-bossbarapi=player-hasflag">BossBarAPI.hasFlag(Player player, Flag flag)</h5>
<p>Pretty self explained. Return boolean (when player has no flag return false).</p>
<h5 id="w-bossbarapi=player-removebar">BossBarAPI.removeBar(Player player)</h5>
<p>Also pretty self explained.</p>
