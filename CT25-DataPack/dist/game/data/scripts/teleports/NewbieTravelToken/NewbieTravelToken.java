package teleports.NewbieTravelToken;

import java.util.HashMap;
import java.util.Map;

import ct25.xtreme.gameserver.model.actor.L2Npc;
import ct25.xtreme.gameserver.model.actor.instance.L2PcInstance;
import ct25.xtreme.gameserver.model.quest.Quest;
import ct25.xtreme.gameserver.model.quest.QuestState;

public class NewbieTravelToken extends Quest
{
	private static final String qn = "NewbieTravelToken";

	private static Map<String, Object[]> data = new HashMap<String, Object[]>();

	private final static int[] NPCs =
	{
		30600,30601,30599,30602,30598,32135
	};

	private void load()
	{
		data.put("30600", new Object[]{12111,16686,-4584});
		data.put("30601", new Object[]{115632,-177996,-896});
		data.put("30599", new Object[]{45475,48359,-3056});
		data.put("30602", new Object[]{-45032,-113598,-192});
		data.put("30598", new Object[]{-84081,243227,-3728});
		data.put("32135", new Object[]{-119697,44532,360});
	}

	public NewbieTravelToken(int questId, String name, String descr)
	{
		super(questId, name, descr);
		for (int id : NPCs)
		{
			addStartNpc(id);
			addTalkId(id);
		}
		load();
	}

	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		if (st == null)
			st = newQuestState(player);

		if (data.containsKey(event))
		{
			int x = (Integer) data.get(event)[0];
			int y = (Integer) data.get(event)[1];
			int z = (Integer) data.get(event)[2];

			player.teleToLocation(x, y, z);
			st.exitQuest(true);
		}

		return super.onAdvEvent(event, npc, player);
	}

	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(getName());
		int npcId = npc.getId();
		if (player.getLevel() >= 20)
		{
			htmltext = "1.htm";
			st.exitQuest(true);
		}
		else
			htmltext = npcId + ".htm";

		return htmltext;
	}

	public static void main(String[] args)
	{
		new NewbieTravelToken(-1, qn, "teleports");
	}
}