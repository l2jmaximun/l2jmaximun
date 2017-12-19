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
 * this program. If not, see <http://L2J.EternityWorld.ru/>.
 */
package village_master.Bitz;

import ct25.xtreme.gameserver.model.actor.L2Npc;
import ct25.xtreme.gameserver.model.actor.instance.L2PcInstance;
import ct25.xtreme.gameserver.model.quest.Quest;

/**
 * Created by LordWinter 27.02.2011
 * Fixed by L2J Eternity-World
 */
public final class Bitz extends Quest
{
	private static final String qn = "Bitz_occupation_change";

	// Quest NPCs
	private static final int BITZ = 30026;

	public Bitz(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(BITZ);
		addTalkId(BITZ);
	}

	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.contains("-01") || event.contains("-02") || event.contains("-03") || event.contains("-04") ||
				event.contains("-05") || event.contains("-06") || event.contains("-07"))
			return event;

		return null;
	}

	@Override
	public String onTalk(L2Npc npc, L2PcInstance talker)
	{
		switch (talker.getClassId())
		{
		case fighter:
			return "30026-01.htm";
		case warrior:
		case knight:
		case rogue:
			return "30026-08.htm";
		case warlord:
		case paladin:
		case treasureHunter:
		case adventurer:
		case hellKnight:
		case dreadnought:
			return "30026-09.htm";
		case gladiator:
		case darkAvenger:
		case hawkeye:
		case sagittarius:
		case phoenixKnight:
		case duelist:
			return "30026-09.htm";
		default:
			return "30026-10.htm";
		}
	}

	public static void main(String[] args)
	{
		new Bitz(-1, qn, "village_master");
	}
}