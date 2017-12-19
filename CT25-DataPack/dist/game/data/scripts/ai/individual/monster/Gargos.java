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
import ct25.xtreme.gameserver.model.actor.L2Npc;
import ct25.xtreme.gameserver.model.actor.instance.L2PcInstance;
import ct25.xtreme.gameserver.model.holders.SkillHolder;
import javolution.util.FastSet;

/**
 * @author InsOmnia
 */
public class Gargos extends L2AttackableAIScript
{
	// Npcs
    private static final int Gargos = 18607;
    
    // Arrays
    private FastSet<Integer> _startedTimmers = new FastSet<Integer>();
    
    // Skill
    private static SkillHolder Fire_Trap = new SkillHolder(5705, 1);

    public Gargos(int questId, String name, String descr)
    {
        super(questId, name, descr);
        addAttackId(Gargos);
        addKillId(Gargos);
    }

    @Override
    public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
    {
        if (event.equalsIgnoreCase("TimeToFire"))
        {
            _startedTimmers.remove(npc.getObjectId());
            npc.broadcastNpcSay("Oooo...ooo...");
            npc.doCast(Fire_Trap.getSkill());
        }
        return super.onAdvEvent(event, npc, player);
    }

    @Override
    public String onAttack(L2Npc npc, L2PcInstance player, int damage, boolean isPet)
    {
        int id = npc.getObjectId();
        if (npc.getId() == Gargos)
        {
            if (!_startedTimmers.contains(id))
            {
                _startedTimmers.add(id);
                this.startQuestTimer("TimeToFire", 60000, npc, player);
            }
        }
        return super.onAttack(npc, player, damage, isPet);
    }

    @Override
    public String onKill(L2Npc npc, L2PcInstance killer, boolean isPet)
    {
        if (npc.getId() == Gargos)
        {
            _startedTimmers.remove(npc.getObjectId());
            this.cancelQuestTimer("TimeToFire", npc, killer);
        }
        return super.onKill(npc, killer, isPet);
    }

    public static void main(String[] args)
    {
        new Gargos(-1, Gargos.class.getSimpleName(), "ai/individual/monster");
    }
}