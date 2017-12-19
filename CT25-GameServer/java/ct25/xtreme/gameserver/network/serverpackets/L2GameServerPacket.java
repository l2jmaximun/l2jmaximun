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
package ct25.xtreme.gameserver.network.serverpackets;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.mmocore.network.SendablePacket;

import ct25.xtreme.Config;
import ct25.xtreme.gameserver.model.interfaces.IPositionable;
import ct25.xtreme.gameserver.model.itemcontainer.Inventory;
import ct25.xtreme.gameserver.network.L2GameClient;

/**
 *
 * @author  KenM
 */
public abstract class L2GameServerPacket extends SendablePacket<L2GameClient>
{
	protected static final Logger _log = Logger.getLogger(L2GameServerPacket.class.getName());
	
	protected boolean _invisible = false;
	
	private static final int[] PAPERDOLL_ORDER = new int[]
	{
		Inventory.PAPERDOLL_UNDER,
		Inventory.PAPERDOLL_REAR,
		Inventory.PAPERDOLL_LEAR,
		Inventory.PAPERDOLL_NECK,
		Inventory.PAPERDOLL_RFINGER,
		Inventory.PAPERDOLL_LFINGER,
		Inventory.PAPERDOLL_HEAD,
		Inventory.PAPERDOLL_RHAND,
		Inventory.PAPERDOLL_LHAND,
		Inventory.PAPERDOLL_GLOVES,
		Inventory.PAPERDOLL_CHEST,
		Inventory.PAPERDOLL_LEGS,
		Inventory.PAPERDOLL_FEET,
		Inventory.PAPERDOLL_CLOAK,
		Inventory.PAPERDOLL_RHAND,
		Inventory.PAPERDOLL_HAIR,
		Inventory.PAPERDOLL_HAIR2,
		Inventory.PAPERDOLL_RBRACELET,
		Inventory.PAPERDOLL_LBRACELET,
		Inventory.PAPERDOLL_DECO1,
		Inventory.PAPERDOLL_DECO2,
		Inventory.PAPERDOLL_DECO3,
		Inventory.PAPERDOLL_DECO4,
		Inventory.PAPERDOLL_DECO5,
		Inventory.PAPERDOLL_DECO6,
		Inventory.PAPERDOLL_BELT
	};
	
	/**
	 * 
	 * @return True if packet originated from invisible character.
	 */
	public boolean isInvisible()
	{
		return _invisible;
	}
	
	/**
	 * Set "invisible" boolean flag in the packet.
	 * Packets from invisible characters will not be broadcasted to players.
	 * @param b
	 */
	public void setInvisible(boolean b)
	{
		_invisible = b;
	}
	
	/**
	 * Writes 3 D (int32) with current location x, y, z
	 * @param loc
	 */
	protected void writeLoc(IPositionable loc)
	{
		writeD(loc.getX());
		writeD(loc.getY());
		writeD(loc.getZ());
	}
	
	protected int[] getPaperdollOrder()
	{
		return PAPERDOLL_ORDER;
	}
	
	/**
	 * @see ct25.xtreme.mmocore.network.SendablePacket#write()
	 */
	@Override
	protected void write()
	{
		try
		{
			writeImpl();
		}
		catch (Exception e)
		{
			_log.log(Level.SEVERE, "Client: " + getClient().toString() + " - Failed writing: " + getClass().getSimpleName() + " - BR Xtreme Server Version: " + Config.SERVER_VERSION + " - DP Revision: " + Config.DATAPACK_VERSION + " ; " + e.getMessage(), e);
		}
	}
	
	public void runImpl()
	{
		
	}
	
	protected abstract void writeImpl();
	
	/**
	 * @return A String with this packet name for debuging purposes
	 */
	public abstract String getType();
}
