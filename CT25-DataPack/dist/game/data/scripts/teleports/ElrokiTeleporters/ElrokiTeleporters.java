package teleports.ElrokiTeleporters;

import ct25.xtreme.gameserver.model.actor.L2Npc;
import ct25.xtreme.gameserver.model.actor.instance.L2PcInstance;
import ct25.xtreme.gameserver.model.quest.Quest;
import ct25.xtreme.gameserver.model.quest.QuestState;

public class ElrokiTeleporters extends Quest
{
	private static final String qn = "ElrokiTeleporters";

	private final static int NPC1 = 32111;
	private final static int NPC2 = 32112;

	public ElrokiTeleporters(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addStartNpc(NPC1,NPC2);
		addTalkId(NPC1,NPC2);
	}

	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		int npcId = npc.getId();
		if (npcId == NPC1)
		{
			if (player.isInCombat())
				htmltext = "32111-no.htm";
			else
				player.teleToLocation(4990, -1879, -3178);
		}
		else if (npcId == NPC2)
			player.teleToLocation(7557, -5513, -3221);

		st.exitQuest(true);
		return htmltext;
	}

	public static void main(String[] args)
	{
		new ElrokiTeleporters(-1, qn, "teleports");
	}
}