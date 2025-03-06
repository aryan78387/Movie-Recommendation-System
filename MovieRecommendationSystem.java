import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Objects;

public class MovieRecommendationSystem {
    private final JFrame frame;
    private final JTextField nameField;
    private final JTextField ageField;
    private final JComboBox<String> genderBox;
    private final JComboBox<String> regionBox;
    private final JComboBox<String> genreBox;
    private final JTextArea descriptionArea;

    public MovieRecommendationSystem() {
        frame = new JFrame("Movie Recommendation System");
        frame.setSize(700, 650);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(20, 20, 20));
        frame.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("FilmFinder");
        titleLabel.setFont(new Font("Serif", Font.ITALIC | Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(20, 20, 20));
        titlePanel.add(titleLabel);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(20, 20, 20));
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        Font labelFont = new Font("Arial", Font.BOLD, 18);
        Font textFont = new Font("Arial", Font.PLAIN, 16);

        JLabel nameLabel = createStyledLabel("Name:", labelFont, Color.WHITE);
        nameField = createStyledTextField(textFont);

        JLabel ageLabel = createStyledLabel("Age:", labelFont, Color.WHITE);
        ageField = createStyledTextField(textFont);

        JLabel genderLabel = createStyledLabel("Gender:", labelFont, Color.WHITE);
        genderBox = createStyledComboBox(new String[]{"Male", "Female", "Other"}, textFont);

        JLabel regionLabel = createStyledLabel("Region:", labelFont, Color.WHITE);
        regionBox = createStyledComboBox(new String[]{"Hollywood", "Bollywood", "Lollywood"}, textFont);

        JLabel genreLabel = createStyledLabel("Favorite Genre:", labelFont, Color.WHITE);
        genreBox = createStyledComboBox(new String[]{"Action", "Drama", "Comedy", "Thriller", "Sci-Fi", "Romance"}, textFont);

        JLabel descriptionLabel = createStyledLabel("Describe yourself:", labelFont, Color.WHITE);
        descriptionArea = new JTextArea(3, 20);
        descriptionArea.setBackground(Color.DARK_GRAY);
        descriptionArea.setForeground(Color.WHITE);
        descriptionArea.setFont(textFont);
        descriptionArea.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        JLabel sloganLabel = createStyledLabel("Let the magic begin!", new Font("Serif", Font.BOLD | Font.ITALIC, 22), Color.RED);

        JButton submitButton = getjButton();

        gbc.gridx = 0; gbc.gridy = 0; mainPanel.add(nameLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; mainPanel.add(nameField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; mainPanel.add(ageLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; mainPanel.add(ageField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; mainPanel.add(genderLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 2; mainPanel.add(genderBox, gbc);
        gbc.gridx = 0; gbc.gridy = 3; mainPanel.add(regionLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 3; mainPanel.add(regionBox, gbc);
        gbc.gridx = 0; gbc.gridy = 4; mainPanel.add(genreLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 4; mainPanel.add(genreBox, gbc);
        gbc.gridx = 0; gbc.gridy = 5; mainPanel.add(descriptionLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 5; mainPanel.add(descriptionArea, gbc);

        gbc.gridx = 0; gbc.gridy = 6; mainPanel.add(sloganLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 6; mainPanel.add(submitButton, gbc);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private JButton getjButton() {
        JButton submitButton = new JButton("Get Movie Recommendations");
        submitButton.setFont(new Font("Arial", Font.BOLD, 16));
        submitButton.setBackground(Color.RED);
        submitButton.setForeground(Color.WHITE);
        submitButton.addActionListener((event) -> {
                if (validateInputs()) {
                    User user = new User(
                            nameField.getText().trim(),
                            Integer.parseInt(ageField.getText().trim()),
                            Objects.requireNonNull(genderBox.getSelectedItem()).toString(),
                            Objects.requireNonNull(regionBox.getSelectedItem()).toString(),
                            Objects.requireNonNull(genreBox.getSelectedItem()).toString(),
                            descriptionArea.getText().trim()
                    );
                    showMovieRecommendations(user);
                }
        });
        return submitButton;
    }

    private boolean validateInputs() {
        String name = nameField.getText().trim();
        String age = ageField.getText().trim();

        if (name.isEmpty() || age.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Name and Age cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!name.matches("[a-zA-Z ]+")) {
            JOptionPane.showMessageDialog(frame, "Name can only contain alphabets!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!age.matches("\\d+")) {
            JOptionPane.showMessageDialog(frame, "Age must be a number!", "Input Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    private JTextField createStyledTextField(Font font) {
        JTextField field = new JTextField(15);
        field.setBackground(Color.BLACK);
        field.setForeground(Color.WHITE);
        field.setFont(font);
        field.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
        return field;
    }

    private JComboBox<String> createStyledComboBox(String[] items, Font font) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setBackground(Color.BLACK);
        comboBox.setForeground(Color.WHITE);
        comboBox.setFont(font);
        return comboBox;
    }

    private void showMovieRecommendations(User user) {
        JFrame movieFrame = new JFrame("Your Movie Recommendations 🎬");
        movieFrame.setSize(400, 500);
        movieFrame.getContentPane().setBackground(new Color(30, 30, 30));
        movieFrame.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(30, 30, 30));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        JLabel slogan = createStyledLabel(" Here's a Movie we suggest!",
                new Font("Serif", Font.BOLD | Font.ITALIC, 24), Color.RED);
        slogan.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = createStyledLabel("Name: " + user.name,
                new Font("Arial", Font.BOLD, 18), Color.WHITE);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel genderLabel = createStyledLabel("Gender: " + user.gender,
                new Font("Arial", Font.PLAIN, 16), Color.WHITE);
        genderLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descriptionLabel = createStyledLabel("About You: " + user.description,
                new Font("Arial", Font.PLAIN, 16), Color.WHITE);
        descriptionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        List<String> movies = MovieFetcher.getMovies(user);
        JLabel movieLabel = createStyledLabel("Recommended Movie: " + movies.getFirst(),
                new Font("Arial", Font.BOLD, 20), Color.ORANGE);
        movieLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(slogan);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(nameLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(genderLabel);
        contentPanel.add(Box.createVerticalStrut(15));
        contentPanel.add(descriptionLabel);
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(movieLabel);

        movieFrame.add(contentPanel, BorderLayout.CENTER);
        movieFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new MovieRecommendationSystem();
    }
}

class User {
    String name, gender, region, genre, description;
    int age;

    public User(String name, int age, String gender, String region, String genre, String description) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.region = region;
        this.genre = genre;
        this.description = description;
    }
}

class MovieFetcher {
    public static List<String> getMovies(User user) {
        String ageGroup = getAgeGroup(user.age);
        String region = user.region;
        String genre = user.genre;

        switch (ageGroup) {
            case "0-10":
                switch (region) {
                    case "Hollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 The Incredibles (2004)");
                            case "Drama": return List.of("🎬 The Lion King (1994)");
                            case "Comedy": return List.of("🎬 Toy Story (1995)");
                            case "Thriller": return List.of("🎬 Coraline (2009)");
                            case "Sci-Fi": return List.of("🎬 Wall-E (2008)");
                            case "Romance": return List.of("🎬 Beauty and the Beast (1991)");
                        }
                        break;
                    case "Bollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 Krrish (2006)");
                            case "Drama": return List.of("🎬 Taare Zameen Par (2007)");
                            case "Comedy": return List.of("🎬 Bhootnath (2008)");
                            case "Thriller": return List.of("🎬 Makdee (2002)");
                            case "Sci-Fi": return List.of("🎬 Robot (2010)");
                            case "Romance": return List.of("🎬 Jab We Met (2007)");
                        }
                        break;
                    case "Lollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 3 Bahadur (2015)");
                            case "Drama": return List.of("🎬 Bol (2011)");
                            case "Comedy": return List.of("🎬 Teefa in Trouble (2018)");
                            case "Thriller": return List.of("🎬 The Donkey King (2018)");
                            case "Sci-Fi": return List.of("🎬 3 Bahadur: Rise of the Warriors (2018)");
                            case "Romance": return List.of("🎬 Heer Ranjha (1970)");
                        }
                        break;
                }
                break;
            case "11-18":
                switch (region) {
                    case "Hollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 Spider-Man: No Way Home (2021)");
                            case "Drama": return List.of("🎬 The Fault in Our Stars (2014)");
                            case "Comedy": return List.of("🎬 Jumanji: Welcome to the Jungle (2017)");
                            case "Thriller": return List.of("🎬 A Quiet Place (2018)");
                            case "Sci-Fi": return List.of("🎬 Interstellar (2014)");
                            case "Romance": return List.of("🎬 The Notebook (2004)");
                        }
                        break;
                    case "Bollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 War (2019)");
                            case "Drama": return List.of("🎬 Udaan (2010)");
                            case "Comedy": return List.of("🎬 Chhichhore (2019)");
                            case "Thriller": return List.of("🎬 Drishyam (2015)");
                            case "Sci-Fi": return List.of("🎬 PK (2014)");
                            case "Romance": return List.of("🎬 Yeh Jawaani Hai Deewani (2013)");
                        }
                        break;
                    case "Lollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 Waar (2013)");
                            case "Drama": return List.of("🎬 Khuda Kay Liye (2007)");
                            case "Comedy": return List.of("🎬 Load Wedding (2018)");
                            case "Thriller": return List.of("🎬 Jalaibee (2015)");
                            case "Sci-Fi": return List.of("🎬 Project Ghazi (2019)");
                            case "Romance": return List.of("🎬 Parey Hut Love (2019)");
                        }
                        break;
                }
                break;
            case "19-30":
                switch (region) {
                    case "Hollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 John Wick (2014)");
                            case "Drama": return List.of("🎬 The Pursuit of Happyness (2006)");
                            case "Comedy": return List.of("🎬 The Hangover (2009)");
                            case "Thriller": return List.of("🎬 Gone Girl (2014)");
                            case "Sci-Fi": return List.of("🎬 Inception (2010)");
                            case "Romance": return List.of("🎬 La La Land (2016)");
                        }
                        break;
                    case "Bollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 KGF: Chapter 1 (2018)");
                            case "Drama": return List.of("🎬 Zindagi Na Milegi Dobara (2011)");
                            case "Comedy": return List.of("🎬 3 Idiots (2009)");
                            case "Thriller": return List.of("🎬 Andhadhun (2018)");
                            case "Sci-Fi": return List.of("🎬 Tumbbad (2018)");
                            case "Romance": return List.of("🎬 Tamasha (2015)");
                        }
                        break;
                    case "Lollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 The Legend of Maula Jatt (2022)");
                            case "Drama": return List.of("🎬 Verna (2017)");
                            case "Comedy": return List.of("🎬 Teefa in Trouble (2018)");
                            case "Thriller": return List.of("🎬 Durj (2019)");
                            case "Sci-Fi": return List.of("🎬 Project Ghazi (2019)");
                            case "Romance": return List.of("🎬 Superstar (2019)");
                        }
                        break;
                }
                break;
            case "31-50":
                switch (region) {
                    case "Hollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 Mad Max: Fury Road (2015)");
                            case "Drama": return List.of("🎬 Forrest Gump (1994)");
                            case "Comedy": return List.of("🎬 The Grand Budapest Hotel (2014)");
                            case "Thriller": return List.of("🎬 Prisoners (2013)");
                            case "Sci-Fi": return List.of("🎬 Blade Runner 2049 (2017)");
                            case "Romance": return List.of("🎬 Titanic (1997)");
                        }
                        break;
                    case "Bollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 Pathaan (2023)");
                            case "Drama": return List.of("🎬 Black (2005)");
                            case "Comedy": return List.of("🎬 Hera Pheri (2000)");
                            case "Thriller": return List.of("🎬 Kahaani (2012)");
                            case "Sci-Fi": return List.of("🎬 Ra.One (2011)");
                            case "Romance": return List.of("🎬 Dilwale Dulhania Le Jayenge (1995)");
                        }
                        break;
                    case "Lollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 The Legend of Maula Jatt (2022)");
                            case "Drama": return List.of("🎬 Manto (2015)");
                            case "Comedy": return List.of("🎬 Actor in Law (2016)");
                            case "Thriller": return List.of("🎬 Bol (2011)");
                            case "Sci-Fi": return List.of("🎬 3 Bahadur Series");
                            case "Romance": return List.of("🎬 Bin Roye (2015)");
                        }
                        break;
                }
                break;
            case "50+":
                switch (region) {
                    case "Hollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 The Equalizer (2014)");
                            case "Drama": return List.of("🎬 The Green Mile (1999)");
                            case "Comedy": return List.of("🎬 The Bucket List (2007)");
                            case "Thriller": return List.of("🎬 The Silence of the Lambs (1991)");
                            case "Sci-Fi": return List.of("🎬 2001: A Space Odyssey (1968)");
                            case "Romance": return List.of("🎬 Casablanca (1942)");
                        }
                        break;
                    case "Bollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 Sholay (1975)");
                            case "Drama": return List.of("🎬 Baghban (2003)");
                            case "Comedy": return List.of("🎬 Piku (2015)");
                            case "Thriller": return List.of("🎬 Badla (2019)");
                            case "Sci-Fi": return List.of("🎬 Koi Mil Gaya (2003)");
                            case "Romance": return List.of("🎬 Mughal-E-Azam (1960)");
                        }
                        break;
                    case "Lollywood":
                        switch (genre) {
                            case "Action": return List.of("🎬 Maula Jatt (1979)");
                            case "Drama": return List.of("🎬 Bol (2011)");
                            case "Comedy": return List.of("🎬 Jawani Phir Nahi Ani (2015)");
                            case "Thriller": return List.of("🎬 Laal Kabootar (2019)");
                            case "Sci-Fi": return List.of("🎬 Project Ghazi (2019)");
                            case "Romance": return List.of("🎬 Heer Ranjha (1970)");
                        }
                        break;
                }
                break;
        }
        return List.of("🎬 No movie found for such combination");
    }

    private static String getAgeGroup(int age) {
        if (age <= 10) return "0-10";
        else if (age <= 18) return "11-18";
        else if (age <= 30) return "19-30";
        else if (age <= 50) return "31-50";
        else return "50+";
    }
}
