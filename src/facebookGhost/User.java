package facebookGhost;

/*import java.net.URL;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;*/

import java.util.ArrayList;

/**
 * Emprunt� � https://github.com/roundrop/facebook4j
 * @author Ryuji Yamashita - roundrop at gmail.com
 */
public interface User {

	String myProfil();
	String getId();
	String getName();
	/* String getFirstName();
    String getMiddleName();
    String getLastName();
    String getGender();
    Locale getLocale();
    List<IdNameEntity> getLanguages();
    URL getLink();
    String getUsername();
    String getThirdPartyId();
    Boolean isInstalled();
    Double getTimezone();
    Date getUpdatedTime();
    Boolean isVerified();
    String getBio();
    String getBirthday();
//    Cover getCover();
    List<User.Education> getEducation();
    String getEmail();
	 */
	IdNameEntity getHometown();
	/*
    List<String> getInterestedIn();
    IdNameEntity getLocation();
    String getPolitical();
    List<IdNameEntity> getFavoriteAthletes();
    List<IdNameEntity> getFavoriteTeams();
//    Picture getPicture();
    String getQuotes();
    String getRelationshipStatus();
    String getReligion();
    IdNameEntity getSignificantOther();
    User.VideoUploadLimits getVideoUploadLimits();
    URL getWebsite();
    List<User.Work> getWork();
;

    interface Education {
        IdNameEntity getYear();
        String getType();
        IdNameEntity getSchool();
        IdNameEntity getDegree();
        List<IdNameEntity> getConcentration();
        List<User.EducationClass> getClasses();
        List<IdNameEntity> getWith();
    }

    interface EducationClass {
        List<IdNameEntity> getWith();
        String getDescription();
    }

    interface VideoUploadLimits {
        long getLength();
        long getSize();
    }

    interface Work {
        IdNameEntity getEmployer();
        IdNameEntity getLocation();
        IdNameEntity getPosition();
        String getStartDate();
        String getEndDate();

    }*/

	User.AgeRange getAgeRange();
	interface AgeRange {
		// one value could be null (13-17 / 18-20 / 21 - null)
		Integer getMin();
		Integer getMax();
		int getAge();
	}
	ArrayList<User> getFriends();
	void addFriend(User friend);
	void addFamily(User familyMember);
	ArrayList <User>getFamily();

	void setLocation(String name);
	//String BIRTHDAY_DATE_FORMAT = "MM/dd/yyyy";




}