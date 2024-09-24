import java.util.Random;//підключаємо пакет для використання класу Random
import java.util.Scanner;//підключаємо пакет для використання класу Scanner

public class AreaShooting {
    public static void main(String[] args) //основний метод "виклику" гри
    {

        Random rand = new Random();//створюємо екземпляр класу Random для генерації псевдовипадкових чисел
        Scanner scanner = new Scanner(System.in);//створюємо екземпляр класу Scanner для вводу даних через консоль
        Game(scanner,rand);
    }
    public static void ShowField(boolean[][] map,int targetRow,int targetColumn)//метод,що демонтрує поле гри для гравця
    {
        for(int i =0;i<map[0].length;i++)
        {
                if(i==0)
                {
                 System.out.println("0 | 1 | 2 | 3 | 4 | 5 |");
                }
                System.out.print(i+1);
            for(int j =0;j<map[1].length;j++)
            {   
                  if(!map[i][j])
                  System.out.print(" | -");
                  else if(map[i][j] && (i!=targetRow || j!=targetColumn))
                  System.out.print(" | *");
                  else if(map[i][j] && i==targetRow && j==targetColumn)
                  System.out.print(" | X");
            }
            System.out.println(" |");
        }
    }
    public  static void Game(Scanner scanner,Random rand)//основний метод,в якому відбувається увесь ігровий процес
    {   
        boolean[][] map = new boolean[5][5];//матриця,що демонструє куди влучив гравець
        boolean flag = false;// булева змінна,що демонструє статус "ураження" цілі
        int targetRow = rand.nextInt(0,5);//"вигадуємо" рядок на якому буде знаходитись ціль
        int targetColumn = rand.nextInt(0,5);//"вигадуємо" стовбець на якому буде знаходитись ціль
        System.out.println("All Set. Get ready to rumble!");
        ShowField(map,targetRow,targetColumn);//друкуємо поле гри
        while(!flag)
        {  
           int[] resultOfInputting = InputtingCoordinates(scanner);//викликаємо мето,де вводимо координати,куди хоче "стріляти" гравець
           int chosenRow=resultOfInputting[0],chosenColumn=resultOfInputting[1];//присвоюємо введені значення змінним,що відображають обрані рядок/стовбець(координати)
           chosenRow--;
           chosenColumn--;
           targetRow++;
           targetColumn++;
           //коригуємо коориднати цілі і обраної мітки так,щоб не виникло помилки,а також задля коректного відображення даних на мапі
           if(!CheckingCoordinatesByBoundary(chosenRow, chosenColumn))//викликаємо метод,що перевіряє введені координати  щодо розмірності поля гри
           {
            //якщо перевірку пройдено,то
               if(targetRow==chosenRow && targetColumn==chosenColumn)//звіряємо введені дані із ціллю
               {
             //якщо влучаємо,то виводимо відповідне повідомлення і закінчужмо гру
                System.out.println("You have won!");
                map[--chosenRow][--chosenColumn] = true;
                flag = true;
               }
               else
               {
                //якщо не влучаємо,то виводимо відповідне повідомлення і продовжуємо гру
                System.out.println("You have missed.Try again.");
                map[--chosenRow][--chosenColumn] = true;
                }
           }
            targetRow--;
            targetColumn--;
            //коригуємо коориднати цілі
            ShowField(map,targetRow,targetColumn);//друкуємо поле гри
        }
    }
    public static boolean CheckingCoordinatesByBoundary(int row,int column)//метод,що перевіряє введені коориданти щодо розмірності поля гри
    { 
       boolean flag = false;//булева змінна,що визначає чи коректно введені координати
       //якщо значення ряду/стовпця більше/менше межі мапи,то виводимо відповідне повідомлення
       if(row > 5 || row < 1)
       {
        System.out.println("Incorrect coordinate by row.Input coordinates again");
        flag = true;
       }
       else if(column > 5 || column < 1)
       {
        System.out.println("Incorrect coordiante bu column.Input coordinates again");
        flag= true;
       }
         return flag;//повертаємо значення змінної,як результат роботи методу
    }

    public static int[] InputtingCoordinates(Scanner scanner)//метод,що відповідає за введення координат
    {   final int startCoordinates=-1;//константа,що потрібна для скидання введених координат при некоректному вводі
        int row=startCoordinates,column=startCoordinates;
        int[] result = new int[2];//ініціалізуємо масив,що повернеться як результат роботи методу
        boolean checking=false;//змінна,що буде відповідати за продовження циклу do...while,допоки не будуть ввдені коректні координати
        do 
        {
         try 
         { //просимо ввести координати по рядкам/стовпцям
            System.out.println("Input row");
            scanner = new Scanner(System.in);
            row =   scanner.nextInt()+1;
            System.out.println("Input column");
            scanner = new Scanner(System.in);
            column = scanner.nextInt()+1;
         } catch (Exception e) 
         {
            //якщо ввденео не цифровий символ,тоді виводимо відповідне повідомлення і ставимо координати "за замовчуванням",аби продовжити цикл
            System.out.println("You entered not a number.Please be more accurate.");
            row = startCoordinates;
            column= startCoordinates;
         }
         finally
         {
            //перевіряємо чи координати не дорівнюють значенням "за замовчуванням"
            if(row!=startCoordinates &&column!=startCoordinates)
            {
                //якщо ж ні,тоді
                checking = true;//змінюємо значення булевої змінної,що відповідає за продовження циклу
                //присвоюємо введені значення змінній,що повернеться,як результат роботи мметоду
                result[0]=row;
                result[1]=column;
            }
         }
        } while (!checking);

       return result;//повертаємо введені значення в основний метод
    }
}
