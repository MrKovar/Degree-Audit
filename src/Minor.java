import java.util.ArrayList;

public class Minor extends Degree
{
	String degreeName;
	ArrayList<String> courseList = new ArrayList<String>();
	ArrayList<ElectiveSet> electiveSetSet = new ArrayList<ElectiveSet>();
	ArrayList<String> takenElectives = new ArrayList<String>();
	
	void setMinorName(String set)
	{
		degreeName = set;
	}
	
	String getDegreeName()
	{
		return degreeName;
	}
	
	void addCourse(String add)
	{
		courseList.add(add);
	}
	
	void removeCourse(String rem)
	{
		courseList.remove(rem);
	}
	
	void addElectiveSet(ElectiveSet set)
	{
		electiveSetSet.add(set);
	}
	
	ArrayList<ElectiveSet> getElectiveSet()
	{
		return electiveSetSet;
	}
	
	ArrayList<String> getCourseList()
	{
		return courseList;
	}
	
	ArrayList<String> getTakenElectives()
	{
		return takenElectives;
	}
	
	void addTakenElective(String str)
	{
		takenElectives.add(str);
	}
	
	int getTotalCoursesRemaining()
	{
		int tcr = courseList.size();
		for(ElectiveSet es: electiveSetSet)
			tcr+=(es.getRequiredHours() - (es.getStartHours() - es.size()) + es.getNCC());
		return tcr;
	}
	

}


