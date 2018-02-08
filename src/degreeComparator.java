import java.util.Comparator;

public class degreeComparator implements Comparator<Degree>
{
		@Override
		public int compare(Degree d1, Degree d2) 
		{
		        return d1.getTotalCoursesRemaining() > d2.getTotalCoursesRemaining() ? 1 : -1;
		}

}
