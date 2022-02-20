import model.NewsInfo;
import services.NewsApiService;
import  Exception.NewsApiException;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Application {
    private static final NewsApiService newsSearchService = NewsApi.getNewsApiService();  // create an object from the library we made for News api, so we can use its methods
    private static final Scanner scanner = new Scanner(System.in);
    private static LastSearch lastSearchOne = new LastSearch();      // 5 objects of the class LastSearch to save users searches
    private static LastSearch lastSearchTwo = new LastSearch();
    private static LastSearch lastSearchTree = new LastSearch();
    private static LastSearch lastSearchFour = new LastSearch();
    private static LastSearch lastSearchFive = new LastSearch();


    public static void main(String[] args) throws NewsApiException {
        String choice;

        while (true) {
            OptionsForActions.mainMenu();
            choice = scanner.nextLine();
            if (choice.equals("1")){
                searchForTopHeadlines();
            }else if (choice.equals("2")) {
                searchForEverything();
            }else if (choice.equals("3")){
                repeatOneOfYourLastSearches(); // calls the method that will return the results from one of the last searches

            }else if (choice.equals("4")){
                break;
            }else {
                System.out.println("You didn't choose from the options please press enter to try again");
                scanner.nextLine();
            }


        }
    }




    private static void searchForEverything() throws NewsApiException {
        boolean flag=true;
        String language="";
        String sources="";
        String from_date;
        String to_date;
        String parameterq="";
        String wantCategory;
        String category;
        while (true) {
            System.out.println("Search by:\n1.Specific source\n2.Search for articles for a specific language" +
                    "\n3.Go back to main menu" +
                    "\nPress the number of your choice");
            String choice = scanner.nextLine();
            try {
                if (choice.equals("1")) {
                    System.out.println("Enter the source id (for multiple sources write the sources id with no spaces and comma separated)");
                    sources = scanner.nextLine();
                    System.out.println("Press the keyword you want, if you don't want to use keyword just press enter");
                    parameterq=scanner.nextLine();
                }else if (choice.equals("2")){
                    System.out.println("Give the language in ISO Alpha 2 form else you will take no results back");
                    language=scanner.nextLine();
                    System.out.println("Do you want to search by a category? If you want press 1 else press anything");
                    wantCategory = scanner.nextLine();
                    if(wantCategory.equals("1")){
                        category=OptionsForActions.categoryChoices();
                        sources= newsSearchService.getSources(language,category);
                        System.out.println("Press the keyword you want, if you don't want to use keyword just press enter.");
                    }else {
                        while (flag) {
                            System.out.println("Press the keyword you want, keep in mind with no keywords you will not take results");
                            parameterq = scanner.nextLine();
                            if (!parameterq.isEmpty()){
                                flag=false;
                            }
                        }
                    }

                }else if (choice.equals("3")){
                    break;
                }else {
                    System.out.println("You didn't choose from the list, please press enter to try again");
                    scanner.nextLine();
                    continue;
                }

                System.out.println("Give the date for the oldest article allowed (max one month before the today). FORM: YYYY-MM-DD"
                    +"If you don't have a preference press enter");
                from_date=scanner.nextLine();
                System.out.println("Give the date for the newest article allowed (max one month before the today). FORM: YYYY-MM-DD"
                    +"If you don't have a preference press enter");
                to_date=scanner.nextLine();
                List<NewsInfo> results =newsSearchService.getEverything(language,sources,from_date,to_date,parameterq);      //takes the result using the method FROM NewsApiServices
                results.forEach(System.out::println);                                                 // which is written in library with artifact id Jsondeser
                LastSearch currentSearch = new LastSearch();      // to store the current search
                currentSearch.setWhatTypeOfSearch("everything");  //set the currentSearch object
                currentSearch.setCountry(language);
                currentSearch.setSources(sources);
                currentSearch.setFrom_date(from_date);
                currentSearch.setTo_date(to_date);
                currentSearch.setParameterq(parameterq);
                insertTheCurrentSearchToHistory(currentSearch);      //call the method which saves the current search to history


                System.out.println("To make a new search press enter");
                scanner.nextLine();
            }catch (NewsApiException | InputMismatchException e){
                System.out.println(e.getMessage());
            }
        }

    }



    private static void searchForTopHeadlines() {           //searchForTopHeadlines gives results for headlines
        String category;            //we need these parameters to get the values that the user wants
        String country;
        while (true) {
            System.out.println("Press the number of the search you want from the list bellow" +
                    "\n1.Search news from your country by category" +
                    "\n2.Search news from another country by category" +
                    "\n3.Go back to main menu");
            String yourChoice = scanner.nextLine();
            try {
                if (yourChoice.equals("1")) {
                    country = newsSearchService.getIpData();
                    category = OptionsForActions.categoryChoices();  // calls the method which has the list of options for category and returns the users' option
                } else if (yourChoice.equals("2")) {
                    System.out.println("Give the country code you want (ISO Alpha 2 form)");
                    country = scanner.nextLine();
                    category = OptionsForActions.categoryChoices();  // calls the method which has the list of options for category and returns the users' option
                } else if (yourChoice.equals("3")) {
                    break;
                } else {
                    System.out.println("You must choose number 1-3");
                    continue;                                           //if his choice is invalid using continue; no results will be printed and the loop will
                                                                        // start over asking for new entries
                }

                List<NewsInfo> results =newsSearchService.getYourTopHeadlines(country,category);      //takes the result using the method FROM NewsApiServices
                results.forEach(System.out::println);                                                 // which is written in library with artifact id Jsondeser
                LastSearch currentSearch = new LastSearch();      // to store the current search
                currentSearch.setWhatTypeOfSearch("top-headlines");  //set the currentSearch object
                currentSearch.setCountry(country);
                currentSearch.setCategory(category);
                insertTheCurrentSearchToHistory(currentSearch);      //call the method which saves the current search to history
                System.out.println("To make a new search press double enter");
                scanner.nextLine();


            }catch (NewsApiException | InputMismatchException e){
            System.out.println(e.getMessage());
            }

        }
    }


    // insertTheCurrentSearchToHistory checks which of our five lastSearch objects is null and set them with the currentSearch
    // if all they have been set to a value then more than five searches have taken place,
    // so we pass the currentSearch as the fifth (the last of the searches) and the fifth as the forth and so on..
    private static void  insertTheCurrentSearchToHistory(LastSearch currentSearch){
        if (lastSearchOne.getWhatTypeOfSearch().equals("")){
            lastSearchOne=currentSearch;
        }else if (lastSearchTwo.getWhatTypeOfSearch().equals("")){
            lastSearchTwo=currentSearch;
        }else if (lastSearchTree.getWhatTypeOfSearch().equals("")){
            lastSearchTree=currentSearch;
        }else if (lastSearchFour.getWhatTypeOfSearch().equals("")){
            lastSearchFour=currentSearch;
        }else if (lastSearchFive.getWhatTypeOfSearch().equals("")){
            lastSearchFive=currentSearch;
        }else {
            lastSearchOne=lastSearchTwo;
            lastSearchTwo=lastSearchTree;
            lastSearchTree=lastSearchFour;
            lastSearchFour=lastSearchFive;
            lastSearchFive=currentSearch;
        }


    }

    private static void repeatOneOfYourLastSearches() throws NewsApiException {

        List<NewsInfo> results;
            System.out.println("Which of your last 1-5 searches do you want to repeat? (1 is the oldest of the five and 5 is the last one)" +
                    "Press the number of your choice.");
            String choice = scanner.nextLine();
            switch (choice){
                case "1":

                    if (lastSearchOne.getWhatTypeOfSearch().equals("top-headlines")){
                        results=newsSearchService.getYourTopHeadlines(lastSearchOne.getCountry(),lastSearchOne.getCategory());
                        results.forEach(System.out::println);
                    }else if (lastSearchOne.getWhatTypeOfSearch().equals("everything")){
                        results=newsSearchService.getEverything(lastSearchOne.getCountry(),lastSearchOne.getSources(),lastSearchOne.getFrom_date(),
                                lastSearchOne.getTo_date(),lastSearchOne.getParameterq());
                        results.forEach(System.out::println);
                    }else {
                        System.out.println("You haven't make any search yet");
                    }
                    break;
                case "2":

                    if (lastSearchTwo.getWhatTypeOfSearch().equals("top-headlines")){
                        results=newsSearchService.getYourTopHeadlines(lastSearchTwo.getCountry(),lastSearchTwo.getCategory());
                        results.forEach(System.out::println);
                    }else if (lastSearchOne.getWhatTypeOfSearch().equals("everything")){
                        results=newsSearchService.getEverything(lastSearchTwo.getCountry(),lastSearchTwo.getSources(),lastSearchTwo.getFrom_date(),
                                lastSearchTwo.getTo_date(),lastSearchTwo.getParameterq());
                        results.forEach(System.out::println);
                    }else {
                        System.out.println("You haven't make a second search yet");
                    }

                case "3":
                    if (lastSearchTree.getWhatTypeOfSearch().equals("top-headlines")){
                        results=newsSearchService.getYourTopHeadlines(lastSearchTree.getCountry(),lastSearchTree.getCategory());
                        results.forEach(System.out::println);

                    }else if (lastSearchTree.getWhatTypeOfSearch().equals("everything")){
                        results=newsSearchService.getEverything(lastSearchTree.getCountry(),lastSearchTree.getSources(),lastSearchTree.getFrom_date(),
                                lastSearchTree.getTo_date(),lastSearchTree.getParameterq());
                        results.forEach(System.out::println);

                    }else {
                        System.out.println("You haven't make a third search yet");
                    }
                    break;
                case "4":
                    if (lastSearchFour.getWhatTypeOfSearch().equals("top-headlines")){
                        results=newsSearchService.getYourTopHeadlines(lastSearchFour.getCountry(),lastSearchFour.getCategory());
                        results.forEach(System.out::println);

                    }else if (lastSearchFour.getWhatTypeOfSearch().equals("everything")){
                        results=newsSearchService.getEverything(lastSearchFour.getCountry(),lastSearchFour.getSources(),lastSearchFour.getFrom_date(),
                                lastSearchFour.getTo_date(),lastSearchFour.getParameterq());
                        results.forEach(System.out::println);

                    }else {
                        System.out.println("You haven't make any search yet 4");
                    }

                    break;
                case "5":
                    if (lastSearchFive.getWhatTypeOfSearch().equals("top-headlines")){
                        results=newsSearchService.getYourTopHeadlines(lastSearchFive.getCountry(),lastSearchFive.getCategory());
                        results.forEach(System.out::println);                                                 // which is written in library with artifact id Jsondeser

                    }else if (lastSearchFive.getWhatTypeOfSearch().equals("everything")){
                        results=newsSearchService.getEverything(lastSearchFive.getCountry(),lastSearchFive.getSources(),lastSearchFive.getFrom_date(),
                                lastSearchFive.getTo_date(),lastSearchFive.getParameterq());
                        results.forEach(System.out::println);                                                 // which is written in library with artifact id Jsondeser

                    }else {
                        System.out.println("You haven't make any search yet.\nPress enter to continue");
                        scanner.nextLine();
                    }

                    break;
                default:
                    System.out.println("You have to choose between 1-5.");
                    break;
            }
            System.out.println("Press Enter to continue");
            scanner.nextLine();


    }


}

