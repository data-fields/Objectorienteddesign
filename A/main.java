public class main {
	public static void main(String [] args)
	{
		Webpage p = new Webpage("CS3500: Object-Oriented Design",
                "http://www.ccs.neu.edu/home/tov/course/cs3500/",
                "August 11, 2014");
		System.out.println(p.citeApa());
		System.out.println(p.citeMla());
		System.out.println("\"CS3500: Object-Oriented Design.\" Web. "
	        + "August 11, 2014 "
	        + "<~http://www.ccs.neu.edu/home/tov/course/cs3500/>.");
		System.out.println("CS3500: Object-Oriented Design. Retrieved "
	        + "August 11, 2014, "
	        + "from http://www.ccs.neu.edu/home/tov/course/cs3500/.");
	}
}