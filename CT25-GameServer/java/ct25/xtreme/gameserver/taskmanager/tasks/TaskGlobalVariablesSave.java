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
package ct25.xtreme.gameserver.taskmanager.tasks;

import ct25.xtreme.gameserver.instancemanager.GlobalVariablesManager;
import ct25.xtreme.gameserver.taskmanager.Task;
import ct25.xtreme.gameserver.taskmanager.TaskManager;
import ct25.xtreme.gameserver.taskmanager.TaskTypes;
import ct25.xtreme.gameserver.taskmanager.TaskManager.ExecutedTask;


/**
 * @author Gigiikun
 */
public class TaskGlobalVariablesSave extends Task
{
	public static final String NAME = "global_varibales_save";
	
	@Override
	public String getName()
	{
		return NAME;
	}
	
	@Override
	public void onTimeElapsed(ExecutedTask task)
	{
		GlobalVariablesManager.getInstance().storeMe();
	}
	
	@Override
	public void initializate()
	{
		super.initializate();
		TaskManager.addUniqueTask(NAME, TaskTypes.TYPE_FIXED_SHEDULED, "500000", "1800000", "");
	}
}