import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class MaximumDegrees
{
	public static void main(String[] args) throws IOException
	{
		Scanner input = new Scanner(System.in);
		String coursesFilename;
		String takenFilename;
		ArrayList<Major> majorList = new ArrayList<Major>();
		ArrayList<Minor> minorList = new ArrayList<Minor>();
		
		
		System.out.println("\t\tMaximum Degrees Calculator");
		System.out.println("***********************************");
		System.out.println("Please enter an input file of majors with the format:");
		System.out.println("Major: X");
		System.out.println("CODE YY1");
		System.out.println("CODE YY2");
		System.out.println("CODE YY3");
		System.out.println();
		System.out.print("Course List: ");
		coursesFilename = input.nextLine();
		System.out.println("Please enter an input file of taken classes with the format:");
		System.out.println("CODE YY1");
		System.out.println("CODE YY2");
		System.out.println("CODE YY3");
		takenFilename = input.nextLine();
		//Scanner course_file_scan = new Scanner(new File(coursesFilename));
		//Scanner taken_file_scan = new Scanner(new File(takenFilename));
		Scanner course_file_scan = new Scanner(new File("InstitutionCourses.txt"));
		Scanner taken_file_scan = new Scanner(new File("takenCourses.txt"));
		
		int majorCtr = -1;
		int minorCtr = -1;
		boolean isMajor = true;
		//Build Majors of INSTITUTION
		while(course_file_scan.hasNextLine()) // While file has next Line
		{
			String pLine = course_file_scan.nextLine();
			String line[] =  pLine.split(" ");// Split it
			if(line[0].equals("Major:")) // If file declares major
			{
				isMajor = true;
				majorCtr++; // increment major counter
				majorList.add(new Major()); // add new major to list
				majorList.get(majorCtr).setMajorName(pLine.substring(line[0].length()).trim()); // set major name
			}
			else if(line[0].equals("Minor:"))
			{
				isMajor = false;
				minorCtr++; // increment major counter
				minorList.add(new Minor()); // add new major to list
				minorList.get(minorCtr).setMinorName(pLine.substring(line[0].length()).trim()); // set major name
			}
			else if(line[0].equals("Pick"))
			{
				int num = Integer.parseInt(line[2]);
				ElectiveSet eleSet = new ElectiveSet(); //create eleset
				for(int a = 0; a < num; a++)
				{
					String line2 = course_file_scan.nextLine();
					eleSet.addElective(new Elective(line2));
					eleSet.setRequiredHours(Integer.parseInt(line[1]));
					eleSet.setStartHours(Integer.parseInt(line[2]));
				}
				if(isMajor)
					majorList.get(majorCtr).addElectiveSet(eleSet);
				else
					minorList.get(minorCtr).addElectiveSet(eleSet);
			}
			else if(pLine.length() > 1)
			{
				if(isMajor)
					majorList.get(majorCtr).addCourse(pLine); // add to major
				else
					minorList.get(minorCtr).addCourse(pLine);
			}
		}
		
		while(taken_file_scan.hasNextLine())
		{
			String line = taken_file_scan.nextLine();
			for(int ii = 0; ii < majorList.size(); ii++)
			{
				//System.out.println(majorList.get(ii).getDegreeName() + " contains " + line + " = " + majorList.get(ii).getCourseList().contains(line));
				if(majorList.get(ii).getCourseList().contains(line))
				{
					
					majorList.get(ii).addTakenElective((majorList.get(ii).getCourseList().get(majorList.get(ii).getCourseList().indexOf(line))));
					//System.out.println("REMOVED A CLASS " + removed.get(ii));
					majorList.get(ii).removeCourse(line);
				}
				else
					for(int j = 0; j < majorList.get(ii).getElectiveSet().size(); j++)
					{
						for(int k = 0; k < majorList.get(ii).getElectiveSet().get(j).size(); k++)
						{
							//System.out.println("In List: " + majorList.get(ii).getElectiveSet().get(j).getElectiveAt(k).getName());
							//System.out.println("Check: " + line);
							//System.out.println(majorList.get(ii).getElectiveSet().get(j).remove(new Elective(line)));
							if(majorList.get(ii).getElectiveSet().get(j).contains(new Elective(line)) && !majorList.get(ii).getTakenElectives().contains(line))
							{
								majorList.get(ii).getElectiveSet().get(j).remove(new Elective(line));
								majorList.get(ii).addTakenElective(line);
							}
							else if(majorList.get(ii).getElectiveSet().get(j).contains(new Elective(line)))
							{
								majorList.get(ii).getElectiveSet().get(j).remove(new Elective(line));
								majorList.get(ii).getElectiveSet().get(j).courseNoCount();
							}
							
						}
					}
			}
		
		}
		Collections.sort(majorList, new degreeComparator());
		Collections.sort(minorList, new degreeComparator());
		
		for(Major m: majorList)
		{
			System.out.println("Classes to Major in " + m.getDegreeName() + " - " + m.getTotalCoursesRemaining() + " Courses Remaining");
			for(String s: m.getCourseList())
			{
				System.out.println(s);
			}
			
			for(ElectiveSet es: m.getElectiveSet())
			{
				if(es.getStartHours() - es.getRequiredHours() < es.size())
				{
					System.out.println("*Pick " + (es.getRequiredHours() - (es.getStartHours() - es.size()) + es.getNCC()) + " of the "
								+ (es.getStartHours() - (es.getStartHours() - es.size()) - es.getNCC()) + "*");
					for(Elective e: es.getElectiveSet())
					{
						System.out.println("*" + e.getName());
					}
				}		
			}
			System.out.println();
		}
			
		for(Minor n: minorList)
		{
			System.out.println("Classes to Minor in " + n.getDegreeName() + " - " + n.getTotalCoursesRemaining() + " Courses Remaining");
			for(String s: n.getCourseList())
			{
				System.out.println(s);
			}
				
			for(ElectiveSet es: n.getElectiveSet())
			{
				if(es.getStartHours() - es.getRequiredHours() < es.size())
				{
					System.out.println("*Pick " + (es.getRequiredHours() - (es.getStartHours() - es.size()) + es.getNCC()) + " of the "
								+ (es.getStartHours() - (es.getStartHours() - es.size()) - es.getNCC()) + "*");
					for(Elective e: es.getElectiveSet())
					{
						System.out.println("*" + e.getName());
					}
				}
						
			}
			System.out.println();
		}
				
			

		
		course_file_scan.close();
		taken_file_scan.close();
		input.close();
		
		
	}

}
