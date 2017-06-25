import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class GUI extends JFrame {
    private TicketSystem ticketSys;
    private final JPanel panel_book_user = new JPanel();
    private JTextField book_userID;
    private JTextField min_min;
    private JTextField max_min;
    private JTextField inq_rate;
    private JTextField inq_ticketID;
    private JTextField inq_amount;
    private JTextField book_amount;
    private JTextField h_after;
    private JTextField m_after;
    private JTextField h_before;
    private JTextField m_before;


    public GUI()
    {
        ticketSys = new TicketSystem();

        setSize(640, 360);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setContentPane(pageIndex());
        pack();
        setVisible(true);
    }

    private JPanel pageIndex(){
        BorderLayout bl_panel_index = new BorderLayout();
        bl_panel_index.setVgap(20);
        bl_panel_index.setHgap(30);
        JPanel panel_index = new JPanel(bl_panel_index);

        JButton btn_book = new JButton("訂票");


        JPanel panel_ref = new JPanel();
        panel_ref.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        JLabel lab_ref = new JLabel("電影票ID：");
        JTextField ref_ticketID = new JTextField(25);
        JButton btn_ref = new JButton("退票");
        btn_ref.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                String ticketID = ref_ticketID.getText();
                new Alert(ticketSys.myRefunder.refund(ticketID));
            }
        });

        JLabel lab_main_ref = new JLabel("<<退票>>");
        lab_main_ref.setFont(new Font("新細明體", Font.PLAIN, 24));
        panel_ref.add(lab_main_ref);

        JSeparator separator = new JSeparator();
        panel_ref.add(separator);
        panel_ref.add(lab_ref, BorderLayout.WEST);
        panel_ref.add(ref_ticketID, BorderLayout.CENTER);
        panel_ref.add(btn_ref, BorderLayout.EAST);


        JPanel panel_lab_inq = new JPanel();
        JLabel lab_inq = new JLabel("<<查詢>>");
        lab_inq.setFont(new Font("新細明體", Font.PLAIN, 24));
        panel_lab_inq.add(lab_inq);
        JPanel panel_btn_inq = new JPanel();

        JPanel panel_inq = new JPanel(new BorderLayout());
        panel_inq.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
        panel_inq.add(panel_lab_inq, BorderLayout.NORTH);
        panel_inq.add(panel_btn_inq, BorderLayout.CENTER);
        GridBagLayout gbl_panel_btn_inq = new GridBagLayout();
        panel_btn_inq.setLayout(gbl_panel_btn_inq);

        JPanel panel_inq_area = new JPanel();
        FlowLayout flowLayout = (FlowLayout) panel_inq_area.getLayout();
        flowLayout.setAlignment(FlowLayout.LEFT);
        GridBagConstraints gbc_panel_inq_area = new GridBagConstraints();
        gbc_panel_inq_area.fill = GridBagConstraints.BOTH;
        gbc_panel_inq_area.insets = new Insets(0, 0, 5, 0);
        gbc_panel_inq_area.gridx = 0;
        gbc_panel_inq_area.gridy = 0;
        panel_btn_inq.add(panel_inq_area, gbc_panel_inq_area);

        ButtonGroup radio_group =new ButtonGroup();

        JRadioButton radio_area = new JRadioButton("");
        panel_inq_area.add(radio_area);
        radio_group.add(radio_area);

        JLabel label = new JLabel("指定區域：");
        panel_inq_area.add(label);

        JComboBox combo_inq_area = new JComboBox();
        combo_inq_area.setModel(new DefaultComboBoxModel(new String[] {"精華區", "最佳位置", "次佳位置"}));
        panel_inq_area.add(combo_inq_area);

        JPanel panel_inq_row = new JPanel();
        FlowLayout flowLayout_1 = (FlowLayout) panel_inq_row.getLayout();
        flowLayout_1.setAlignment(FlowLayout.LEFT);
        GridBagConstraints gbc_panel_inq_row = new GridBagConstraints();
        gbc_panel_inq_row.fill = GridBagConstraints.BOTH;
        gbc_panel_inq_row.insets = new Insets(0, 0, 5, 0);
        gbc_panel_inq_row.gridx = 0;
        gbc_panel_inq_row.gridy = 1;
        panel_btn_inq.add(panel_inq_row, gbc_panel_inq_row);

        JRadioButton radio_row = new JRadioButton("");
        panel_inq_row.add(radio_row);
        radio_group.add(radio_row);

        JLabel label_1 = new JLabel("指定排數：");
        panel_inq_row.add(label_1);

        JComboBox combo_inq_row = new JComboBox();
        combo_inq_row.setModel(new DefaultComboBoxModel(new String[] {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M"}));
        panel_inq_row.add(combo_inq_row);

        JPanel panel_inq_time = new JPanel();
        GridBagConstraints gbc_panel_inq_time = new GridBagConstraints();
        gbc_panel_inq_time.gridheight = 2;
        gbc_panel_inq_time.fill = GridBagConstraints.BOTH;
        gbc_panel_inq_time.insets = new Insets(0, 0, 5, 0);
        gbc_panel_inq_time.gridx = 0;
        gbc_panel_inq_time.gridy = 2;
        panel_btn_inq.add(panel_inq_time, gbc_panel_inq_time);
        GridBagLayout gbl_panel_inq_time = new GridBagLayout();
        panel_inq_time.setLayout(gbl_panel_inq_time);

        JPanel panel_1 = new JPanel();
        FlowLayout flowLayout_6 = (FlowLayout) panel_1.getLayout();
        flowLayout_6.setAlignment(FlowLayout.LEFT);
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.gridheight = 2;
        gbc_panel_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_panel_1.anchor = GridBagConstraints.WEST;
        gbc_panel_1.insets = new Insets(0, 0, 5, 5);
        gbc_panel_1.gridx = 0;
        gbc_panel_1.gridy = 0;
        panel_inq_time.add(panel_1, gbc_panel_1);

        JRadioButton radio_time = new JRadioButton("");
        panel_1.add(radio_time);
        radio_group.add(radio_time);

        JPanel panel_2 = new JPanel();
        GridBagConstraints gbc_panel_2 = new GridBagConstraints();
        gbc_panel_2.anchor = GridBagConstraints.NORTHEAST;
        gbc_panel_2.insets = new Insets(0, 0, 5, 5);
        gbc_panel_2.gridx = 1;
        gbc_panel_2.gridy = 0;
        panel_inq_time.add(panel_2, gbc_panel_2);

        h_after = new JTextField();
        panel_2.add(h_after);
        h_after.setColumns(2);

        JLabel lblNewLabel_1 = new JLabel("時");
        panel_2.add(lblNewLabel_1);

        m_after = new JTextField();
        m_after.setColumns(2);
        panel_2.add(m_after);

        JLabel lblNewLabel_2 = new JLabel("分以後、");
        panel_2.add(lblNewLabel_2);

        h_before = new JTextField();
        h_before.setColumns(2);
        panel_2.add(h_before);

        JLabel lblNewLabel_3 = new JLabel("時");
        panel_2.add(lblNewLabel_3);

        m_before = new JTextField();
        m_before.setColumns(2);
        panel_2.add(m_before);

        JLabel lblNewLabel_4 = new JLabel("分以前");
        panel_2.add(lblNewLabel_4);

        JPanel panel_3 = new JPanel();
        GridBagConstraints gbc_panel_3 = new GridBagConstraints();
        gbc_panel_3.insets = new Insets(0, 0, 5, 5);
        gbc_panel_3.anchor = GridBagConstraints.SOUTHEAST;
        gbc_panel_3.gridx = 1;
        gbc_panel_3.gridy = 1;
        panel_inq_time.add(panel_3, gbc_panel_3);

        JLabel lblNewLabel_6 = new JLabel("片長介於");
        panel_3.add(lblNewLabel_6);

        min_min = new JTextField();
        panel_3.add(min_min);
        min_min.setColumns(5);

        JLabel lblNewLabel_5 = new JLabel("分至");
        panel_3.add(lblNewLabel_5);

        max_min = new JTextField();
        panel_3.add(max_min);
        max_min.setColumns(5);

        JLabel lblNewLabel_7 = new JLabel("分之間");
        panel_3.add(lblNewLabel_7);

        JPanel panel = new JPanel();
        FlowLayout flowLayout_5 = (FlowLayout) panel.getLayout();
        flowLayout_5.setAlignment(FlowLayout.RIGHT);
        GridBagConstraints gbc_panel = new GridBagConstraints();
        gbc_panel.fill = GridBagConstraints.BOTH;
        gbc_panel.insets = new Insets(0, 0, 5, 0);
        gbc_panel.gridx = 0;
        gbc_panel.gridy = 4;
        panel_btn_inq.add(panel, gbc_panel);

        JLabel label_4 = new JLabel("選擇以上條件後輸入需求座位數：");
        panel.add(label_4);

        inq_amount = new JTextField();
        inq_amount.setColumns(5);
        panel.add(inq_amount);

        JButton btn_inq_amount = new JButton("查詢");
        btn_inq_amount.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int amount = 0;
                try{
                    amount = Integer.parseInt(inq_amount.getText());
                }catch(NumberFormatException err){}

                if(radio_area.isSelected()){
                    String area = combo_inq_area.getSelectedItem().toString();
                    new Alert(ticketSys.myInq.specificSeats(amount, area, "none"));
                }else if(radio_row.isSelected()){
                    String row = combo_inq_row.getSelectedItem().toString();
                    new Alert(ticketSys.myInq.specificSeats(amount, "none", row));
                }else if(radio_time.isSelected()){
                    //沒做非數字的處理
                    String after = h_after.getText() + "：" + m_after.getText();
                    String before = h_before.getText() + "：" + m_before.getText();
                    String min = min_min.getText();
                    String max = max_min.getText();
                    new Alert(ticketSys.myInq.byTime(amount, after, before, min, max));
                }
            }
        });
        panel.add(btn_inq_amount);

        JSeparator separator_1 = new JSeparator();
        GridBagConstraints gbc_separator_1 = new GridBagConstraints();
        gbc_separator_1.fill = GridBagConstraints.BOTH;
        gbc_separator_1.insets = new Insets(0, 0, 5, 0);
        gbc_separator_1.gridx = 0;
        gbc_separator_1.gridy = 5;
        panel_btn_inq.add(separator_1, gbc_separator_1);

        JPanel panel_inq_rate = new JPanel();
        FlowLayout flowLayout_2 = (FlowLayout) panel_inq_rate.getLayout();
        flowLayout_2.setAlignment(FlowLayout.RIGHT);
        GridBagConstraints gbc_panel_inq_rate = new GridBagConstraints();
        gbc_panel_inq_rate.fill = GridBagConstraints.BOTH;
        gbc_panel_inq_rate.insets = new Insets(0, 0, 5, 0);
        gbc_panel_inq_rate.gridx = 0;
        gbc_panel_inq_rate.gridy = 6;
        panel_btn_inq.add(panel_inq_rate, gbc_panel_inq_rate);

        JLabel lblNewLabel_9 = new JLabel("評價");
        panel_inq_rate.add(lblNewLabel_9);

        inq_rate = new JTextField();
        panel_inq_rate.add(inq_rate);
        inq_rate.setColumns(10);

        JLabel lblNewLabel_8 = new JLabel("分以上電影");
        panel_inq_rate.add(lblNewLabel_8);

        JButton btn_inq_rate = new JButton("查詢");
        btn_inq_rate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String in_rate = inq_rate.getText();
                Double rate = Double.parseDouble(in_rate);
                new Alert(ticketSys.myInq.byScore(rate));
            }
        });
        panel_inq_rate.add(btn_inq_rate);

        JPanel panel_inq_movie = new JPanel();
        FlowLayout flowLayout_3 = (FlowLayout) panel_inq_movie.getLayout();
        flowLayout_3.setAlignment(FlowLayout.RIGHT);
        GridBagConstraints gbc_panel_inq_movie = new GridBagConstraints();
        gbc_panel_inq_movie.fill = GridBagConstraints.BOTH;
        gbc_panel_inq_movie.insets = new Insets(0, 0, 5, 0);
        gbc_panel_inq_movie.gridx = 0;
        gbc_panel_inq_movie.gridy = 7;
        panel_btn_inq.add(panel_inq_movie, gbc_panel_inq_movie);

        JLabel label_2 = new JLabel("電影：");
        panel_inq_movie.add(label_2);

        JComboBox combo_inq_movie = new JComboBox();

        String[] movieList = ticketSys.movieDB.getAllMovieNames();
        combo_inq_movie.setModel(new DefaultComboBoxModel(movieList));
        panel_inq_movie.add(combo_inq_movie);

        JButton btn_inq_movie = new JButton("資訊查詢");
        btn_inq_movie.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String movieID = ticketSys.movieDB.getIDByName(combo_inq_movie.getSelectedItem().toString());
                new Alert(ticketSys.myInq.movieInfo(movieID));
            }
        });
        panel_inq_movie.add(btn_inq_movie);

        JPanel panel_inq_ticket = new JPanel();
        FlowLayout flowLayout_4 = (FlowLayout) panel_inq_ticket.getLayout();
        flowLayout_4.setAlignment(FlowLayout.RIGHT);
        GridBagConstraints gbc_panel_inq_ticket = new GridBagConstraints();
        gbc_panel_inq_ticket.insets = new Insets(0, 0, 5, 0);
        gbc_panel_inq_ticket.fill = GridBagConstraints.BOTH;
        gbc_panel_inq_ticket.gridx = 0;
        gbc_panel_inq_ticket.gridy = 8;
        panel_btn_inq.add(panel_inq_ticket, gbc_panel_inq_ticket);

        JLabel lblid = new JLabel("電影票ID：");
        panel_inq_ticket.add(lblid);

        inq_ticketID = new JTextField();
        inq_ticketID.setColumns(15);
        panel_inq_ticket.add(inq_ticketID);

        JButton btn_inq_ticket = new JButton("資訊查詢");
        btn_inq_ticket.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String ticketID = inq_ticketID.getText();
                new Alert(ticketSys.myInq.ticketInfo(ticketID));
            }
        });
        panel_inq_ticket.add(btn_inq_ticket);
        panel_index.add(panel_ref, BorderLayout.SOUTH);
        //panel_index.add(btn_book, BorderLayout.CENTER);
        panel_index.add(pageBook(), BorderLayout.CENTER);
        panel_index.add(panel_inq, BorderLayout.EAST);

        return panel_index;
    }
    private JPanel pageBook(){
        //it used to be in pageBook() below
        JPanel panel_book = new JPanel();
        panel_book.setLayout(new GridLayout(8, 1, 0, 8));

        JLabel label_book = new JLabel("<<訂票>>");
        label_book.setFont(new Font("新細明體", Font.PLAIN, 24));
        label_book.setHorizontalAlignment(SwingConstants.CENTER);
        panel_book.add(label_book);
        panel_book.add(panel_book_user);

        JLabel lblNewLabel = new JLabel("請輸入使用者ID：");
        panel_book_user.add(lblNewLabel);

        book_userID = new JTextField();
        panel_book_user.add(book_userID);
        book_userID.setColumns(22);

        JPanel panel_book_movie = new JPanel();
        panel_book.add(panel_book_movie);

        JLabel lblNewLabel_11 = new JLabel("請選擇電影：");
        panel_book_movie.add(lblNewLabel_11);

        JComboBox combo_movie = new JComboBox();
        panel_book_movie.add(combo_movie);
        String[] movieList = ticketSys.movieDB.getAllMovieNames();
        combo_movie.setModel(new DefaultComboBoxModel(movieList));

        JPanel panel_book_time = new JPanel();
        panel_book.add(panel_book_time);

        JLabel lblNewLabel_12 = new JLabel("請選擇時間：");
        panel_book_time.add(lblNewLabel_12);

        JComboBox combo_time = new JComboBox();
        panel_book_time.add(combo_time);
        String[] timeList = ticketSys.movieDB.getStartByName(movieList[0]);
        combo_time.setModel(new DefaultComboBoxModel(timeList));
        combo_movie.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                String movie = (String) e.getItem();
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    String[] timeList = ticketSys.movieDB.getStartByName(movie);
                    combo_time.setModel(new DefaultComboBoxModel(timeList));
                }
            }
        });

        JPanel panel_book_special = new JPanel();
        panel_book.add(panel_book_special);

        JLabel lblNewLabel_13 = new JLabel("請選擇座位要求：");
        panel_book_special.add(lblNewLabel_13);

        JComboBox combo_special = new JComboBox();
        panel_book_special.add(combo_special);
        combo_special.setModel(new DefaultComboBoxModel(new String[] {"無", "精華區", "最佳位置", "次佳位置", "A排", "B排", "C排", "D排", "E排", "F排", "G排", "H排", "I排", "J排", "K排", "L排", "M排"}));

        JPanel panel_book_cont = new JPanel();
        panel_book.add(panel_book_cont);

        JLabel lblNewLabel_14 = new JLabel("座位是否須連續：");
        panel_book_cont.add(lblNewLabel_14);

        JComboBox combo_cont = new JComboBox();
        panel_book_cont.add(combo_cont);
        combo_cont.setModel(new DefaultComboBoxModel(new String[] {"否", "是"}));


        panel_book.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));

        JLabel label_blank = new JLabel("");
        panel_book.add(label_blank);

        JPanel panel = new JPanel();
        panel_book.add(panel);

        JLabel lab_book_amount = new JLabel("張數：");
        panel.add(lab_book_amount);

        book_amount = new JTextField();
        panel.add(book_amount);
        book_amount.setColumns(10);

        JButton btn_book = new JButton("訂票！");
        btn_book.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String in_userID = book_userID.getText();
                String in_movieName = combo_movie.getSelectedItem().toString();
                String in_time = combo_time.getSelectedItem().toString();
                String in_special = combo_special.getSelectedItem().toString();
                String in_cont = combo_cont.getSelectedItem().toString();
                String in_amount = book_amount.getText();

                String movieID = ticketSys.movieDB.getIDByName(in_movieName);
                String area = "none";
                String row = "none";
                if(in_special.indexOf("排")>-1) row = in_special.substring(0,1);
                else if(!in_special.equals("無")) area = in_special;
                if(area.equals("精華區")) area = "red";
                else if(area.equals("最佳位置")) area = "yellow";
                else if(area.equals("次佳位置")) area = "blue";
                boolean cont = (in_cont.equals("是"))? true: false;
                int amount = 0;
                try{
                    amount = Integer.parseInt(in_amount);
                }catch(NumberFormatException err){} //for anyone inputs something other than numbers in book_amount

                if(!cont && area.equals("none") && row.equals("none")) new Alert(ticketSys.myBooker.generalBook(in_userID, movieID, in_time, amount));
                else new Alert(ticketSys.myBooker.conditionalBook(in_userID, movieID, in_time, amount, cont, area, row));
            }
        });
        panel.add(btn_book);

        return panel_book;
    }


}
