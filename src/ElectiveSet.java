import java.util.ArrayList;

public class ElectiveSet 
{
	ArrayList<Elective> elective_set = new ArrayList<Elective>();
	int start_hours;
	int required_hours;
	int no_count_courses = 0;
	
	ArrayList<Elective> getElectiveSet()
	{
		return elective_set;
	}
	
	void addElective(Elective e)
	{
		elective_set.add(e);
	}
	
	void setRequiredHours(int rh)
	{
		required_hours = rh;
	}
	
	int getRequiredHours()
	{
		return required_hours;
	}
	
	void setStartHours(int sh)
	{
		start_hours = sh;
	}
	
	int getStartHours()
	{
		return start_hours;
	}
	
	int size()
	{
		return elective_set.size();
	}
	
	void courseNoCount()
	{
		no_count_courses++;
	}
	
	int getNCC()
	{
		return no_count_courses;
	}
	
	String remove(Elective e)
	{
		boolean removed = false;
		int removeAt = 0;
		for(int i = 0; i < elective_set.size(); i++)
		{
			if(elective_set.get(i).getName().equals(e.getName()))
			{
				removeAt = i;
				removed = true;
			}
		}
		if(removed)
		{
			elective_set.remove(removeAt);
			return e.getName();
		}
		return "nah fam, not here";
		
	}
	
	Elective getElectiveAt(int at)
	{
		return elective_set.get(at);
	}
	
	boolean contains(Elective e)
	{
		for(Elective el: elective_set)
		{
			if(el.getName().equals(e.getName()))
				return true;
		}
		return false;
	}
	
}
