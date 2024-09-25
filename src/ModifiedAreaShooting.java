import java.util.Random;//підключаємо пакет для використання класу Random
import java.util.Scanner;//підключаємо пакет для використання класу Scanner

public class ModifiedAreaShooting 
{
    public static void main(String[] args)//основний метод "виклику" модифікованої гри
    {
    
        Random rand = new Random();//створюємо екземпляр класу Random для генерації псевдовипадкових чисел
        Scanner scanner = new Scanner(System.in);//створюємо екземпляр класу Scanner для вводу даних через консоль
        Game(scanner,rand);
    }
    public static void ShowField(boolean[][] map,int[][] target)//метод,що демонтрує поле гри для гравця
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
                  if(map[i][j]==false)
                  System.out.print(" | -");
                  //якщо ні одна частина цілі не уражена,друкуємо відповідний символ
                  else if(map[i][j]==true && ((i!=target[0][0] && i!=target[0][1] && i!=target[0][2]) || (j!=target[1][0] && j!=target[1][1] && j!=target[1][2])))
                  System.out.print(" | *");
                  //якщо хоч одна частина цілі уражена,внаслідок введення координат,то друкуємо відповідний символ
                  else if(map[i][j]==true && (i==target[0][0] || i==target[0][1] ||i==target[0][2]) && (j==target[1][0] ||j==target[1][1] || j==target[1][2]))
                  System.out.print(" | X");
            }
            System.out.println(" |");
        }
    }
    public  static void Game(Scanner scanner,Random rand)//основний метод,в якому відбувається увесь ігровий процес
    {   
        boolean[][] map = new boolean[5][5];//матриця,що демонструє куди влучив гравець
        boolean[] flag = new boolean[3];// булевий масив,що демонструє статус ураженості кожної з 3 частин цілі
        flag[0]=false;flag[1]=false;flag[2]=false;//ініціалізуємо значення кожного елементу булевого масиву
        int[][] target = new int[2][3];//створюємо цілочисельний масив,котрий відовідатиме за збереження координат кожної частини цілі
        int counter=0;//лічільник,котрий рахує скільки разів було влучено в ціль
        target=CreatingTarget(rand);//викликаємо метод,що задає координати цілі передавши його значення в цілочисельний масив призначений для координат цілі
        System.out.println("All Set. Get ready to rumble!");
        ShowField(map,target);//викликаємо метод,що демонструє ігрове поле гравцю
        while(flag[0]==false || flag[1]==false || flag[2]==false)//допоки всі частини цілі не уражено продовжуємо гру
        {  
           int[] resultOfInputting = InputtingCoordinates(scanner);//вводимо координати,через відповідний метод
           int chosenRow=resultOfInputting[0],chosenColumn=resultOfInputting[1];//присвоюємо введені значення змінним,що відображають обрані рядок/стовбець(координати)
           chosenRow--;
           chosenColumn--;
           target[0][0]++;target[0][1]++;target[0][2]++;
           target[1][0]++;target[1][1]++;target[1][2]++;
            //коригуємо коориднати цілі і обраної мітки так,щоб не виникло помилки,а також задля коректного відображення даних на мапі
           if(CheckingCoordinatesByBoundary(chosenRow, chosenColumn))//викликаємо метод,що перевіряє введені координати  щодо розмірності поля гри
           continue;
           else
           {
            //якщо перевірку пройдено,то звіряємо введені дані із ціллю
            if((target[0][0]==chosenRow||target[0][1]==chosenRow ||target[0][2]==chosenRow) && (target[1][0]==chosenColumn || target[1][1]==chosenColumn || target[1][2]==chosenColumn))
            {
              //якщо влучаємо,то виводимо відповідне повідомлення 
             System.out.println("You have hitted the target");
             map[--chosenRow][--chosenColumn] = true;//позначаємо відповідну частину,як уражену
             flag[counter] = true;
             counter++;//збільшуємо значення лічільника
            }
            else//інакше
            {
              //якщо не влучаємо,то виводимо відповідне повідомлення і продовжуємо гру
             System.out.println("You have missed.Try again.");
             map[--chosenRow][--chosenColumn] = true;
            }
            target[0][0]--;
            target[0][1]--;
            target[0][2]--;
            target[1][0]--;
            target[1][1]--;
            target[1][2]--;
            //коригуємо коориднати частин цілі
            ShowField(map,target);
           }
        }
        System.out.println("You have won the game!");//по завершенню циклу,друкуємо повідомлення,що гравець виграв
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
         {
          //просимо ввести координати по рядкам/стовпцям
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
    public static int[][] CreatingTarget(Random random)//метод,що створює ціль
    { final int TargetSize = 3;//константа,що позначає розмір цілі
       int[][] target = new int[2][TargetSize];//створюємо матрицю,котра буде зберігати координати частин цілі по рядкам/стовпцям
       boolean flag = false;//булева змінна,що відповідає за статус "створенності цілі"
       do 
       { 
        //"загадуємо" цифру від 0 до 5 по рядкам/стовпцям нашої мапи
        int row = random.nextInt(5);//"загадуємо" цифру від 0 до 5 по рядкам/стовпцям нашої мапи
        int column = random.nextInt(5);
        if(row<=2)//якщо  загадане значення по рядкам менше/дорівню 2,то розташовуємо ціль горизонтально
        {
         for(int i=0;i<target[1].length;i++)//вносимо через цикл координати по рядкам
         {
           target[0][i]=row;
         }
         int j = random.nextInt(0,2);
         int counter =0;
         for(int l = j;counter<TargetSize;l++)//вносимо через цикл координати по стовпцям
         {
           target[1][counter]=l;
           counter++;
         }
         flag = true;//позначаємо ціль як створену
        }
       else if(column<=2)// інашке,якщо  загадане значення по стовпцям менше/дорівню 2,то розташовуємо ціль вертикально
       {
        for(int i=0;i<target[1].length;i++)//вносимо через цикл координати по стовпцям
        {
           target[1][i]=column;
        }
        int i = random.nextInt(0,2);
        int counter =0;
        for(int l = i;counter<TargetSize;l++)//вносимо через цикл координати по рядкам
        {
          target[0][counter]=l;
          counter++;
        }
        flag = true;//позначаємо ціль як створену
      }
     } while (flag==false);//робимо все це допоки ціль не буде створено
     return target;//повертаємо всі 3 координати цілі
    }
}
