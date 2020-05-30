package com.company;


import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Main extends JFrame {
    String file;
    String newfile;
    static int pos =-1;
    static int rexind =-1;
    static int current_rex=-1;
    static List<Integer> regexindex = new ArrayList<>();
    static List<Integer> regexindexend = new ArrayList<>();
    public Main() {
        super("XXX");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        //setLayout(null);
        initComponents();
        setVisible(true);
    }

    private void initComponents() {

        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setName("FileChooser");

        JPanel north = new JPanel(new FlowLayout());
        JTextArea TextArea = new JTextArea();
        TextArea.setName("TextArea");


        TextArea.setColumns(35);
        TextArea.setRows(25);

        JTextField searchfield = new JTextField(15);
        searchfield.setName("SearchField");

        Icon saveic = new ImageIcon("save.png");
        JButton SaveButton = new JButton(saveic);
        SaveButton.setName("SaveButton");

        Icon loadic = new ImageIcon("loadic.png");
        JButton LoadButton = new JButton(loadic);
        LoadButton.setName("OpenButton");

        Icon start_search = new ImageIcon("search.png");
        JButton startsearch = new JButton(start_search);
        startsearch.setName("StartSearchButton");


        Icon previous = new ImageIcon("prev.png");
        JButton prev = new JButton(previous);
        prev.setName("PreviousMatchButton");

        Icon nextic = new ImageIcon("next.png");
        JButton next = new JButton(nextic);
        next.setName("NextMatchButton");

        JCheckBox regex_cb = new JCheckBox("Regex");
        regex_cb.setName("UseRegExCheckbox");

        north.add(SaveButton );
        north.add(LoadButton );
        north.add(searchfield );
        north.add(startsearch );
        north.add(prev );
        north.add(next );
        north.add(regex_cb );

        add(north, BorderLayout.NORTH);

        JPanel center = new JPanel(new FlowLayout());


        JScrollPane ScrollPane = new JScrollPane(TextArea);

        ScrollPane.setName("ScrollPane");

        ScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        center.add(ScrollPane);
        //center.add(TextArea);
        add(center, BorderLayout.CENTER);



        SaveButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent event){
                jfc.setVisible(true);
                int returnValue = jfc.showSaveDialog(null);

                File selectedFile=new File("");
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = jfc.getSelectedFile();
                }
               // jfc.setVisible(false);


                file = selectedFile.getAbsolutePath();



                try(FileWriter writer = new FileWriter(file,false)){

                    writer.write(TextArea.getText());

                } catch (Exception e) {
                    System.out.println("Cannot write file: " + e.getMessage());
                }

            }
        });



        LoadButton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent event) {

                int returnValue = jfc.showOpenDialog(null);
                File selectedFile=new File("");
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = jfc.getSelectedFile();
                }

                file = selectedFile.getAbsolutePath();



                if (new File(file).exists()) {
                    try {
                        TextArea.setText(readFileAsString(file));
                    } catch (Exception e) {
                        System.out.println("Cannot read file: " + e.getMessage());
                    }
                } else {
                    TextArea.removeAll();
                    TextArea.setText("");
                }

            }
        });

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        fileMenu.setName("MenuFile");
        fileMenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem load = new JMenuItem("Load");
        load.setName("MenuOpen");
        JMenuItem save = new JMenuItem("Save");
        save.setName("MenuSave");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setName("MenuExit");

        fileMenu.add(load);
        fileMenu.add(save);
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);
     //   setJMenuBar(menuBar);


        // second menu



        JMenu searchmenu = new JMenu("Search");
        searchmenu.setName("MenuSearch");
        searchmenu.setMnemonic(KeyEvent.VK_F);

        JMenuItem startsearchmenu = new JMenuItem("Start Search");
        startsearchmenu.setName("MenuStartSearch");
        JMenuItem prev_menu = new JMenuItem("Previous march");
        prev_menu.setName("MenuPreviousMatch");
        JMenuItem next_menu = new JMenuItem("Next match");
        next_menu.setName("MenuNextMatch");
        JMenuItem use_regex = new JMenuItem("Use regex");
        use_regex.setName("MenuUseRegExp");

        searchmenu.add(startsearchmenu);
        searchmenu.add(prev_menu);
        searchmenu.add(next_menu);
        searchmenu.add(use_regex);
        menuBar.add(searchmenu);
        setJMenuBar(menuBar);











        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                int returnValue = jfc.showOpenDialog(null);
                File selectedFile=new File("");
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = jfc.getSelectedFile();
                }

                    file = selectedFile.getAbsolutePath();

                if (new File(file).exists()) {
                    try {
                        TextArea.setText(readFileAsString(file));
                    } catch (Exception e) {
                        System.out.println("Cannot read file: " + e.getMessage());
                    }
                } else {
                    TextArea.removeAll();
                    TextArea.setText("");
                }

            }
        });

        save.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent event){
                jfc.setVisible(true);
                int returnValue = jfc.showSaveDialog(null);

                File selectedFile=new File("");
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = jfc.getSelectedFile();
                }
                //jfc.setVisible(false);


                file = selectedFile.getAbsolutePath();


                try(FileWriter writer = new FileWriter(file,false)){

                    writer.write(TextArea.getText());

                } catch (Exception e) {
                    System.out.println("Cannot write file: " + e.getMessage());
                }

            }
        });


        // you can rewrite it with a lambda if you prefer this
        exitMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });




    //search
        startsearch.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent event){

                // All code inside SwingWorker runs on a seperate thread
                SwingWorker<String, Void> worker = new SwingWorker<String , Void>() {
                    @Override
                    public String doInBackground() {
                       String text = TextArea.getText();
                       String find = searchfield.getText();
                        if(!regex_cb.isSelected()) {
                            Main.pos = text.indexOf(find);
                            if (Main.pos != -1) {

                                TextArea.setCaretPosition(Main.pos + find.length());
                                TextArea.select(Main.pos, Main.pos + find.length());
                                TextArea.grabFocus();
                            }
                        } else {
                            Pattern pattern = Pattern.compile(find);
                            Matcher matcher = pattern.matcher(text);
                            regexindex = new ArrayList<>();
                            regexindexend = new ArrayList<>();
                            while(matcher.find()) {
                                regexindex.add(matcher.start());
                                regexindexend.add(matcher.end());
                            }
                            if(matcher.find(0)) {
                                current_rex=0;
                                //Main.rexind=matcher.start()+1;
                                TextArea.setCaretPosition(matcher.end());
                                TextArea.select(matcher.start(), matcher.end());
                                TextArea.grabFocus();
                            }

                        }
                        return "1";
                    }
                    @Override
                    public void done() {
                    }
                };

                // Call the SwingWorker from within the Swing thread
                worker.execute();
            }

        });
        next.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent event){

                // All code inside SwingWorker runs on a seperate thread
                SwingWorker<String, Void> worker = new SwingWorker<String , Void>() {
                    @Override
                    public String doInBackground() {
                        String text = TextArea.getText();
                        String find = searchfield.getText();

                        if(!regex_cb.isSelected()) {
                            int tmp = text.indexOf(find);

                            if (tmp != -1) {
                                if (Main.pos == text.lastIndexOf(find)) Main.pos = -1;
                                Main.pos = text.indexOf(find, Main.pos + 1);
                                TextArea.setCaretPosition(Main.pos + find.length());
                                TextArea.select(Main.pos, Main.pos + find.length());
                                TextArea.grabFocus();
                                if (Main.pos == text.lastIndexOf(find)) {
                                    Main.pos = -1;
                                }
                            }

                        }else { // regex
                            Pattern pattern = Pattern.compile(find);
                            Matcher matcher = pattern.matcher(text);
                            Main.regexindex = new ArrayList<>();
                            Main.regexindexend = new ArrayList<>();
                            while(matcher.find()) {
                                Main.regexindex.add(matcher.start());
                                Main.regexindexend.add(matcher.end());
                                System.out.println(matcher.start()+" "+matcher.end());
                            }
                            Matcher matcher1 = pattern.matcher(text);
                            if(matcher1.find()) {
                                current_rex++;
                               // System.out.println(current_rex);
                              //  System.out.println(Main.regexindex.size());
                                if(current_rex==Main.regexindex.size()) current_rex=0;
                              //  System.out.println(current_rex);
                                TextArea.setCaretPosition(Main.regexindexend.get(current_rex));
                                TextArea.select(Main.regexindex.get(current_rex), Main.regexindexend.get(current_rex));
                                TextArea.grabFocus();

                            }


                        }
                        return "1";
                    }
                    @Override
                    public void done() {
                    }
                };

                // Call the SwingWorker from within the Swing thread
                worker.execute();
            }

        });

        prev.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent event){

                // All code inside SwingWorker runs on a seperate thread
                SwingWorker<String, Void> worker = new SwingWorker<String , Void>() {
                    @Override
                    public String doInBackground() {
                        String text = TextArea.getText();
                        String find = searchfield.getText();
                        if(!regex_cb.isSelected()) {
                            int tmp = text.lastIndexOf(find);

                            if (tmp != -1) {
                                Main.pos = text.lastIndexOf(find, Main.pos - 1);
                                if (Main.pos != -1) {
                                    TextArea.setCaretPosition(Main.pos + find.length());
                                    TextArea.select(Main.pos, Main.pos + find.length());
                                    TextArea.grabFocus();
                                }
                                if (Main.pos == -1) {
                                    Main.pos = text.lastIndexOf(find);
                                    TextArea.setCaretPosition(Main.pos + find.length());
                                    TextArea.select(Main.pos, Main.pos + find.length());
                                    TextArea.grabFocus();
                                }
                            }

                        } else { // regex

                            Pattern pattern = Pattern.compile(find);
                            Matcher matcher = pattern.matcher(text);
                            regexindex = new ArrayList<>();
                            regexindexend = new ArrayList<>();
                            while(matcher.find()) {
                                regexindex.add(matcher.start());
                                regexindexend.add(matcher.end());
                            }
                            Matcher matcher1 = pattern.matcher(text);
                            if(matcher1.find()) {
                                current_rex--;
                                if(current_rex==-1) current_rex=regexindex.size()-1;
                                TextArea.setCaretPosition(Main.regexindexend.get(current_rex));
                                TextArea.select(Main.regexindex.get(current_rex), Main.regexindexend.get(current_rex));
                                TextArea.grabFocus();

                            }


                        }
                        return "1";
                    }
                    @Override
                    public void done() {
                    }
                };

                // Call the SwingWorker from within the Swing thread
                worker.execute();
            }

        });




        // menu buttons search

        startsearchmenu.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent event){

                // All code inside SwingWorker runs on a seperate thread
                SwingWorker<String, Void> worker = new SwingWorker<String , Void>() {
                    @Override
                    public String doInBackground() {
                        String text = TextArea.getText();
                        String find = searchfield.getText();
                        if(!regex_cb.isSelected()) {
                            Main.pos = text.indexOf(find);
                            if (Main.pos != -1) {

                                TextArea.setCaretPosition(Main.pos + find.length());
                                TextArea.select(Main.pos, Main.pos + find.length());
                                TextArea.grabFocus();
                            }
                        } else {
                            Pattern pattern = Pattern.compile(find);
                            Matcher matcher = pattern.matcher(text);
                            regexindex = new ArrayList<>();
                            regexindexend = new ArrayList<>();
                            while(matcher.find()) {
                                regexindex.add(matcher.start());
                                regexindexend.add(matcher.end());
                            }
                            if(matcher.find(0)) {
                                current_rex=0;
                                //Main.rexind=matcher.start()+1;
                                TextArea.setCaretPosition(matcher.end());
                                TextArea.select(matcher.start(), matcher.end());
                                TextArea.grabFocus();
                            }

                        }
                        return "1";
                    }
                    @Override
                    public void done() {
                    }
                };

                // Call the SwingWorker from within the Swing thread
                worker.execute();
            }

        });
        next_menu.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent event){

                // All code inside SwingWorker runs on a seperate thread
                SwingWorker<String, Void> worker = new SwingWorker<String , Void>() {
                    @Override
                    public String doInBackground() {
                        String text = TextArea.getText();
                        String find = searchfield.getText();

                        if(!regex_cb.isSelected()) {
                            int tmp = text.indexOf(find);

                            if (tmp != -1) {
                                if (Main.pos == text.lastIndexOf(find)) Main.pos = -1;
                                Main.pos = text.indexOf(find, Main.pos + 1);
                                TextArea.setCaretPosition(Main.pos + find.length());
                                TextArea.select(Main.pos, Main.pos + find.length());
                                TextArea.grabFocus();
                                if (Main.pos == text.lastIndexOf(find)) {
                                    Main.pos = -1;
                                }
                            }

                        }else { // regex
                            Pattern pattern = Pattern.compile(find);
                            Matcher matcher = pattern.matcher(text);
                            Main.regexindex = new ArrayList<>();
                            Main.regexindexend = new ArrayList<>();
                            while(matcher.find()) {
                                Main.regexindex.add(matcher.start());
                                Main.regexindexend.add(matcher.end());
                                System.out.println(matcher.start()+" "+matcher.end());
                            }
                            Matcher matcher1 = pattern.matcher(text);
                            if(matcher1.find()) {
                                current_rex++;
                                // System.out.println(current_rex);
                                //  System.out.println(Main.regexindex.size());
                                if(current_rex==Main.regexindex.size()) current_rex=0;
                                //  System.out.println(current_rex);
                                TextArea.setCaretPosition(Main.regexindexend.get(current_rex));
                                TextArea.select(Main.regexindex.get(current_rex), Main.regexindexend.get(current_rex));
                                TextArea.grabFocus();

                            }


                        }
                        return "1";
                    }
                    @Override
                    public void done() {
                    }
                };

                // Call the SwingWorker from within the Swing thread
                worker.execute();
            }

        });

        prev_menu.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent event){

                // All code inside SwingWorker runs on a seperate thread
                SwingWorker<String, Void> worker = new SwingWorker<String , Void>() {
                    @Override
                    public String doInBackground() {
                        String text = TextArea.getText();
                        String find = searchfield.getText();
                        if(!regex_cb.isSelected()) {
                            int tmp = text.lastIndexOf(find);

                            if (tmp != -1) {
                                Main.pos = text.lastIndexOf(find, Main.pos - 1);
                                if (Main.pos != -1) {
                                    TextArea.setCaretPosition(Main.pos + find.length());
                                    TextArea.select(Main.pos, Main.pos + find.length());
                                    TextArea.grabFocus();
                                }
                                if (Main.pos == -1) {
                                    Main.pos = text.lastIndexOf(find);
                                    TextArea.setCaretPosition(Main.pos + find.length());
                                    TextArea.select(Main.pos, Main.pos + find.length());
                                    TextArea.grabFocus();
                                }
                            }

                        } else { // regex

                            Pattern pattern = Pattern.compile(find);
                            Matcher matcher = pattern.matcher(text);
                            regexindex = new ArrayList<>();
                            regexindexend = new ArrayList<>();
                            while(matcher.find()) {
                                regexindex.add(matcher.start());
                                regexindexend.add(matcher.end());
                            }
                            Matcher matcher1 = pattern.matcher(text);
                            if(matcher1.find()) {
                                current_rex--;
                                if(current_rex==-1) current_rex=regexindex.size()-1;
                                TextArea.setCaretPosition(Main.regexindexend.get(current_rex));
                                TextArea.select(Main.regexindex.get(current_rex), Main.regexindexend.get(current_rex));
                                TextArea.grabFocus();

                            }


                        }
                        return "1";
                    }
                    @Override
                    public void done() {
                    }
                };

                // Call the SwingWorker from within the Swing thread
                worker.execute();
            }

        });

        use_regex.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent event){

                // All code inside SwingWorker runs on a seperate thread
                SwingWorker<String, Void> worker = new SwingWorker<String , Void>() {
                    @Override
                    public String doInBackground() {
                        regex_cb.setSelected((regex_cb.isSelected()?false:true));
                        return "1";
                    }
                    @Override
                    public void done() {
                    }
                };

                // Call the SwingWorker from within the Swing thread
                worker.execute();
            }

        });







    }

    public static String readFileAsString(String fileName) throws IOException {

        return new String(Files.readAllBytes(Paths.get(fileName)));
    }

    public static void main(String[] args) throws Exception{
        Main s = new Main();
    }
}
