package Client;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ProfileRightPanel extends JPanel {
    private JScrollPane school_sp, job_sp;
    private JPanel school_p, job_p;
    private ArrayList<SchoolPanel> school_l = new ArrayList<>();
    private ArrayList<JobPanel> job_l = new ArrayList<>();
    private JLabel school, job;
    private int id;

    public ProfileRightPanel(){
        this.setBounds(465,15,480,580);
        this.setOpaque(false);
        this.setLayout(null);

        //set up the panel of school
        {
            school = new JLabel("Education");
            school.setBounds(10,5,400,30);
            school.setFont(new Font("TimesNewRome", Font.ITALIC, 20));
            school.setForeground(new Color(0,0,125));
            school.setVerticalAlignment(JLabel.CENTER);
            school.setHorizontalAlignment(JLabel.CENTER);

            school_p = new JPanel();
            school_p.setLayout(null);
            school_p.add(school);
            school_p.setBackground(new Color(0xdd, 0xff, 0xee));

            school_sp = new JScrollPane(school_p);
            school_sp.setBounds(0,0,450,250);
            school_sp.setBackground(new Color(0xdd, 0xff, 0xee));
            this.add(school_sp);
        }

        //set up the panel of job
        {
            job = new JLabel("Job");
            job.setBounds(10,5,400,30);
            job.setFont(new Font("TimesNewRome", Font.ITALIC, 20));
            job.setForeground(new Color(0,125,0));
            job.setVerticalAlignment(JLabel.CENTER);
            job.setHorizontalAlignment(JLabel.CENTER);

            job_p = new JPanel();
            job_p.setLayout(null);
            job_p.add(job);
            job_p.setBackground(new Color(0xdd, 0xff, 0xee));

            job_sp = new JScrollPane(job_p);
            job_sp.setBounds(0,260,450,250);
            job_sp.setBackground(new Color(0xdd, 0xff, 0xee));
            this.add(job_sp);
        }
    }

    public void addSchoolJobById(int ID){
        id = ID;
        ArrayList<ArrayList<String>> school_list = GaChatDataClient.getSchoolInfoById(id);
        school_l.clear();
        for(int i = 0; i < school_list.size(); i ++){
            school_l.add(new SchoolPanel(school_list.get(i)));
        }
        for(int i = 0; i < school_l.size(); i ++ ){
            SchoolPanel x = school_l.get(i);
            x.setBounds(5, i * 100 + 40, 420, 90);
            school_p.add(x);
        }
        ArrayList<ArrayList<String>> job_list = GaChatDataClient.getJobInfoById(id);
        job_l.clear();
        for(int i = 0; i < job_list.size(); i ++){
            job_l.add(new JobPanel(job_list.get(i)));
        }
        for(int i = 0; i < job_l.size(); i ++ ){
            JobPanel x = job_l.get(i);
            x.setBounds(5, i * 100 + 40, 420, 90);
            job_p.add(x);
        }
    }

    private class JobPanel extends JPanel{
        private JLabel left_title, left_employer, left_time;
        private JTextArea right_title, right_employer, right_time;

        public JobPanel(ArrayList<String> al){
            this.setSize(420,90);
            this.setLayout(null);
            this.setOpaque(false);

            //set title of this job
            {
                left_title = new JLabel("Job Title:");
                left_title.setBounds(5,5,100,20);
                left_title.setHorizontalAlignment(JLabel.RIGHT);
                left_title.setVerticalAlignment(JLabel.TOP);
                right_title = new JTextArea(al.get(0));
                right_title.setLineWrap(true);
                right_title.setEditable(false);
                right_title.setOpaque(false);
                right_title.setBounds(110,5,300,20);
                this.add(left_title);
                this.add(right_title);
            }

            //set employer of this job
            {
                left_employer = new JLabel("Employer:");
                left_employer.setBounds(5,30,100,20);
                left_employer.setHorizontalAlignment(JLabel.RIGHT);
                left_employer.setVerticalAlignment(JLabel.TOP);
                right_employer = new JTextArea(al.get(1));
                right_employer.setLineWrap(true);
                right_employer.setEditable(false);
                right_employer.setOpaque(false);
                right_employer.setBounds(110,30,300,20);
                this.add(left_employer);
                this.add(right_employer);
            }

            //set working time of this job
            {
                left_time = new JLabel("Working Time:");
                left_time.setBounds(5,55,100,20);
                left_time.setHorizontalAlignment(JLabel.RIGHT);
                left_time.setVerticalAlignment(JLabel.TOP);
                right_time = new JTextArea(al.get(2));
                if(al.size() >= 3 && !(al.get(3).equals(""))){
                    right_time.append(" - " + al.get(3));
                }
                right_time.setLineWrap(true);
                right_time.setEditable(false);
                right_time.setOpaque(false);
                right_time.setBounds(110,55,300,20);
                this.add(left_time);
                this.add(right_time);
            }
        }
    }

    private class SchoolPanel extends JPanel{
        private JLabel left_school, left_major, left_graduation;
        private JTextArea right_school, right_major, right_graduation;

        public SchoolPanel(ArrayList<String> al){
            this.setSize(420,90);
            this.setLayout(null);
            this.setOpaque(false);

            //set name of this educational institution
            {
                left_school = new JLabel("School:");
                left_school.setBounds(5,5,100,20);
                left_school.setHorizontalAlignment(JLabel.RIGHT);
                left_school.setVerticalAlignment(JLabel.TOP);
                right_school = new JTextArea(al.get(0));
                right_school.setLineWrap(true);
                right_school.setEditable(false);
                right_school.setOpaque(false);
                right_school.setBounds(110,5,300,20);
                this.add(left_school);
                this.add(right_school);
            }

            //set the major of this degree
            {
                if(al.size() >= 2 &&  ! al.get(1).equals("")){
                    left_major = new JLabel("Major:");
                    left_major.setBounds(5,30,100,20);
                    left_major.setHorizontalAlignment(JLabel.RIGHT);
                    left_major.setVerticalAlignment(JLabel.TOP);
                    right_major = new JTextArea(al.get(1));
                    right_major.setLineWrap(true);
                    right_major.setEditable(false);
                    right_major.setOpaque(false);
                    right_major.setBounds(110,30,300,20);
                    this.add(left_major);
                    this.add(right_major);
                }
            }

            //set the graduation data if appllicable
            {
                if(al.size() >= 3 && ! al.get(2).equals("")){
                    left_graduation = new JLabel("Graduation Date:");
                    left_graduation.setBounds(5,55,100,20);
                    left_graduation.setHorizontalAlignment(JLabel.RIGHT);
                    left_graduation.setVerticalAlignment(JLabel.TOP);
                    right_graduation = new JTextArea(al.get(2));
                    right_graduation.setLineWrap(true);
                    right_graduation.setEditable(false);
                    right_graduation.setOpaque(false);
                    right_graduation.setBounds(110,55,300,20);
                    this.add(left_graduation);
                    this.add(right_graduation);
                }
            }
        }

    }
}
