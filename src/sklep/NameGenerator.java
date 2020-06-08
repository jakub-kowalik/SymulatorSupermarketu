package sklep;

import java.util.Random;

public final class NameGenerator {

    private NameGenerator(){}

    static Random random = new Random();
    private static String[] firstNames = {"Anna", "Maria", "Katarzyna", "Małgorzata", "Agnieszka", "Krystyna", "Barbara", "Ewa", "Elżbieta", "Zofia", "Janina", "Teresa", "Joanna", "Magdalena", "Monika", "Jadwiga", "Danuta", "Irena", "Halina", "Helena", "Beata", "Aleksandra", "Marta", "Dorota", "Marianna", "Grażyna", "Jolanta", "Stanisława", "Iwona", "Karolina", "Bożena", "Urszula", "Justyna", "Renata", "Alicja", "Paulina", "Sylwia", "Natalia", "Wanda", "Agata", "Aneta", "Izabela", "Ewelina", "Marzena", "Wiesława", "Genowefa", "Patrycja", "Kazimiera", "Edyta", "Stefania", "Jan", "Andrzej", "Piotr", "Krzysztof", "Stanisław", "Tomasz", "Paweł", "Józef", "Marcin", "Marek", "Michał", "Grzegorz", "Jerzy", "Tadeusz", "Adam", "Łukasz", "Zbigniew", "Ryszard", "Dariusz", "Henryk", "Mariusz", "Kazimierz", "Wojciech", "Robert", "Mateusz", "Marian", "Rafał", "Jacek", "Janusz", "Mirosław", "Maciej", "Sławomir", "Jarosław", "Kamil", "Wiesław", "Roman", "Władysław", "Jakub", "Artur", "Zdzisław", "Edward", "Mieczysław", "Damian", "Dawid", "Przemysław", "Sebastian", "Czesław", "Leszek", "Daniel", "Waldemar"};
    private static String[] lastNames = {"Nowak", "Kowalski", "Wiśniewski", "Dąbrowski", "Lewandowski", "Wójcik", "Kamiński", "Kowalczyk", "Zieliński", "Szymański", "Woźniak", "Kozłowski", "Jankowski", "Wojciechowski", "Kwiatkowski", "Kaczmarek", "Mazur", "Krawczyk", "Piotrowski", "Grabowski", "Nowakowski", "Pawłowski", "Michalski", "Nowicki", "Adamczyk", "Dudek", "Zając", "Wieczorek", "Jabłoński", "Król", "Majewski", "Olszewski", "Jaworski", "Wróbel", "Malinowski", "Pawlak", "Witkowski", "Walczak", "Stępień", "Górski", "Rutkowski", "Michalak", "Sikora", "Ostrowski", "Baran", "Duda", "Szewczyk", "Tomaszewski", "Pietrzak", "Marciniak", "Wróblewski", "Zalewski", "Jakubowski", "Jasiński", "Zawadzki", "Sadowski", "Bąk", "Chmielewski", "Włodarczyk", "Borkowski", "Czarnecki", "Sawicki", "Sokołowski", "Urbański", "Kubiak", "Maciejewski", "Szczepański", "Kucharski", "Wilk", "Kalinowski", "Lis", "Mazurek", "Wysocki", "Adamski", "Kaźmierczak", "Wasilewski", "Sobczak", "Czerwiński", "Andrzejewski", "Cieślak", "Głowacki", "Zakrzewski", "Kołodziej", "Sikorski", "Krajewski", "Gajewski", "Szymczak", "Szulc", "Baranowski", "Laskowski", "Brzeziński", "Makowski", "Ziółkowski", "Przybylski"};

    public static String randomName() {
        return firstNames[random.nextInt(firstNames.length)] + " " + lastNames[random.nextInt(lastNames.length)];
    }
}
