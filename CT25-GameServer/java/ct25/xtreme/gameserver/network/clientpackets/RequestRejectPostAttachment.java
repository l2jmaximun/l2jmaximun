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
package ct25.xtreme.gameserver.network.clientpackets;

import static ct25.xtreme.gameserver.model.actor.L2Character.ZONE_PEACE;

import ct25.xtreme.Config;
import ct25.xtreme.gameserver.instancemanager.MailManager;
import ct25.xtreme.gameserver.model.L2World;
import ct25.xtreme.gameserver.model.actor.instance.L2PcInstance;
import ct25.xtreme.gameserver.model.entity.Message;
import ct25.xtreme.gameserver.network.SystemMessageId;
import ct25.xtreme.gameserver.network.serverpackets.ExChangePostState;
import ct25.xtreme.gameserver.network.serverpackets.SystemMessage;
import ct25.xtreme.gameserver.util.Util;

/**
 * @author Migi, DS
 */
public final class RequestRejectPostAttachment extends L2GameClientPacket
{
	private static final String _C__D0_6B_REQUESTREJECTPOSTATTACHMENT = "[C] D0:6B RequestRejectPostAttachment";
	
	private int _msgId;
	
	@Override
	protected void readImpl()
	{
		_msgId = readD();
	}
	
	@Override
	public void runImpl()
	{
		if (!Config.ALLOW_MAIL || !Config.ALLOW_ATTACHMENTS)
			return;
		
		final L2PcInstance activeChar = getClient().getActiveChar();
		if (activeChar == null)
			return;
		
		if (!getClient().getFloodProtectors().getTransaction().tryPerformAction("rejectattach"))
			return;
		
		if (!activeChar.isInsideZone(ZONE_PEACE))
		{
			activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.CANT_USE_MAIL_OUTSIDE_PEACE_ZONE));
			return;
		}
		
		Message msg = MailManager.getInstance().getMessage(_msgId);
		if (msg == null)
			return;
		
		if (msg.getReceiverId() != activeChar.getObjectId())
		{
			Util.handleIllegalPlayerAction(activeChar,
					"Player "+activeChar.getName()+" tried to reject not own attachment!", Config.DEFAULT_PUNISH);
			return;
		}
		
		if (!msg.hasAttachments() || msg.isFourStars())
			return;
		
		MailManager.getInstance().sendMessage(new Message(msg));
		
		activeChar.sendPacket(SystemMessage.getSystemMessage(SystemMessageId.MAIL_SUCCESSFULLY_RETURNED));
		activeChar.sendPacket(new ExChangePostState(true, _msgId, Message.REJECTED));
		
		final L2PcInstance sender = L2World.getInstance().getPlayer(msg.getSenderId());
		if (sender != null)
		{
			SystemMessage sm = SystemMessage.getSystemMessage(SystemMessageId.S1_RETURNED_MAIL);
			sm.addCharName(activeChar);
			sender.sendPacket(sm);
		}
	}
	
	@Override
	public String getType()
	{
		return _C__D0_6B_REQUESTREJECTPOSTATTACHMENT;
	}
}
