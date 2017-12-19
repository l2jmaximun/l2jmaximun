/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ct25.xtreme.gameserver.model.actor.instance;

import java.util.StringTokenizer;

import ct25.xtreme.gameserver.model.actor.L2Npc;
import ct25.xtreme.gameserver.network.SystemMessageId;
import ct25.xtreme.gameserver.network.serverpackets.ActionFailed;
import ct25.xtreme.gameserver.network.serverpackets.ExBrExtraUserInfo;
import ct25.xtreme.gameserver.network.serverpackets.NpcHtmlMessage;
import ct25.xtreme.gameserver.network.serverpackets.SystemMessage;
import ct25.xtreme.gameserver.network.serverpackets.UserInfo;
import ct25.xtreme.gameserver.templates.chars.L2NpcTemplate;

/**
 * Reputation score manager
 * @author Kerberos
 */
public class L2FameManagerInstance extends L2Npc
{
	public L2FameManagerInstance(int objectId, L2NpcTemplate template)
	{
		super(objectId, template);
		setInstanceType(InstanceType.L2FameManagerInstance);
	}
	
	@Override
	public void onBypassFeedback(L2PcInstance player, String command)
	{
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualCommand = st.nextToken();
		if (actualCommand.equalsIgnoreCase("PK_Count"))
		{
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			if (player.getFame() >= 5000 && player.getClassId().level() >= 2 && player.getClan() != null && player.getClan().getLevel() >= 5)
			{
				if (player.getPkKills() > 0)
				{
					player.setFame(player.getFame()-5000);
					player.setPkKills(player.getPkKills()-1);
					player.sendPacket(new UserInfo(player));
					player.sendPacket(new ExBrExtraUserInfo(player));
					html.setFile(player.getHtmlPrefix(), "data/html/famemanager/"+getId()+"-3.htm");
				}
				else
					html.setFile(player.getHtmlPrefix(), "data/html/famemanager/"+getId()+"-4.htm");
			}
			else
				html.setFile(player.getHtmlPrefix(), "data/html/famemanager/"+getId()+"-lowfame.htm");
			
			sendHtmlMessage(player, html);
			return;
		}
		else if (actualCommand.equalsIgnoreCase("CRP"))
		{
			NpcHtmlMessage html = new NpcHtmlMessage(1);
			if (player.getClan() != null && player.getClan().getLevel() >= 5)
			{
				if (player.getFame() >= 1000 && player.getClassId().level() >= 2)
				{
					player.setFame(player.getFame()-1000);
					player.sendPacket(new UserInfo(player));
					player.sendPacket(new ExBrExtraUserInfo(player));
					player.getClan().addReputationScore(50, true);
					player.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.ACQUIRED_50_CLAN_FAME_POINTS));
					html.setFile(player.getHtmlPrefix(), "data/html/famemanager/"+getId()+"-5.htm");
				}
				else
					html.setFile(player.getHtmlPrefix(), "data/html/famemanager/"+getId()+"-lowfame.htm");
			}
			else
				html.setFile(player.getHtmlPrefix(), "data/html/famemanager/"+getId()+"-noclan.htm");
			
			sendHtmlMessage(player, html);
			return;
		}
		else
			super.onBypassFeedback(player, command);
	}
	
	private void sendHtmlMessage(L2PcInstance player, NpcHtmlMessage html)
	{
		html.replace("%objectId%", String.valueOf(getObjectId()));
		player.sendPacket(html);
	}
	
	@Override
	public void showChatWindow(L2PcInstance player)
	{
		player.sendPacket(ActionFailed.STATIC_PACKET);
		String filename = "data/html/famemanager/"+getId()+"-lowfame.htm";
		
		if (player.getFame() > 0)
			filename = "data/html/famemanager/"+getId()+".htm";
		
		NpcHtmlMessage html = new NpcHtmlMessage(1);
		html.setFile(player.getHtmlPrefix(), filename);
		html.replace("%objectId%", String.valueOf(getObjectId()));
		player.sendPacket(html);
	}
}