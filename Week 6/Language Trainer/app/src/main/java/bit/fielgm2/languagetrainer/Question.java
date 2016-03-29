package bit.fielgm2.languagetrainer;

/**
 * Created by Greg on 29/03/2016.
 */
public class Question
{
    private String noun;
    private String englishTranslation;
    private String article;
    private String gender;
    private String image;

    public Question(String noun, String englishTranslation, String article, String gender, String image)
    {
        this.noun = noun;
        this.englishTranslation = englishTranslation;
        this.article = article;
        this.gender = gender;
        this.setImage(image);
    }
    //getters
    public String getNoun(){return noun;}
    public String getEnglishTranslation(){return englishTranslation;}
    public String getArticle(){return article;}
    public String getGender(){return gender;}
    public String getImage(){return image;}
    //setters
    public void setNoun(String noun){this.noun = noun;}
    public void setEnglishTranslation(String englishTranslation){this.englishTranslation = englishTranslation;}
    public void setArticle(String article){this.article = article; }
    public void setGender(String gender){this.gender = gender;}
    public void setImage(String image){this.image = image;}
}
