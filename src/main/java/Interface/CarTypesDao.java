package Interface;


import Entities.CarTypes;

import java.util.List;
import java.util.Scanner;

public interface CarTypesDao {
    CarTypes getCarTypeById(int typeId);
    void updateRoomType(Scanner scanner);
    
    void deleteCarType(Scanner scanner);
    List getAllCarTypes();
}