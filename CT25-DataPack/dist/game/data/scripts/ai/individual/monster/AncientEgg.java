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
package ai.individual.monster;

import ai.engines.L2AttackableAIScript;
import ct25.xtreme.gameserver.model.L2Skill;
import ct25.xtreme.gameserver.model.actor.L2Npc;
import ct25.xtreme.gameserver.model.actor.instance.L2PcInstance;
import ct25.xtreme.gameserver.model.holders.SkillHolder;

public class AncientEgg extends L2AttackableAIScript
{
	// Npc
	private static final int EGG = 18344;
	
	// Skill
	private static SkillHolder SIGNAL = new SkillHolder(5088, 1);
	
	public AncientEgg(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addAttackId(EGG);
	}
	
	@Override
	public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isPet, L2Skill skill)
	{
		player.setTarget(player);
		player.doCast(SIGNAL.getSkill());
		
		return "";
	}
	
	public static void main(String[] args)
	{
		new AncientEgg(-1, AncientEgg.class.getSimpleName(), "ai/individual/monster");
	}
}
