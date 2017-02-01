package mymineralcollection.example.org.mymineral.LoadActivity.CreateAutocompleteList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Santiago on 9/26/2016.
 */
public class CreateListMethods {

    public static List<int[]> breakIntoSteps(int _numOfElements, int _devideBy){

        int _cellNum = (int) Math.ceil(_numOfElements/ _devideBy);

        List<int[]> _cellsList = new ArrayList<>();
        /**
         {startNum,endNum,-1};
         First num - startNum
         Second Num - endNum
         Third Num - If processed.
         //-1 need processing
         //0 in process
         //1 done
         When all elements of list have 1 combine list.
         **/
        for (int index = 0; index <= _devideBy; index++){
            int _endNum = 0;
            if(index < _devideBy) {
                if (index > 0) {

                    int _oldStartNum = _cellsList.get(index - 1)[0];
                    int _oldEndNum = _cellsList.get(index - 1)[1];

                    int _startNum = _oldStartNum + _cellNum;
                    _endNum = _oldEndNum + _cellNum;

                    int[] _cell =  {_startNum,_endNum};
                    _cellsList.add(_cell);
                } else {
                    int[] _cell =  {0,_cellNum};
                    _cellsList.add(_cell);
                }
            }
            if(index == _devideBy){
                int[] lastCell = _cellsList.get(index - 1);

                int _difference = Math.abs(_endNum-_numOfElements);
                _difference = _difference + _endNum;

                _cellsList.remove(_cellsList.size()-1);

                int[] _cell =  {lastCell[0],_difference};
                _cellsList.add(_cell);
            }
        }
        return _cellsList;
    }




    /** -------------------------------------------------------------------------------------- **/


}
