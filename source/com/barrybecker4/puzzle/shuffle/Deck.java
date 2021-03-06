/** Copyright by Barry G. Becker, 2000-2011. Licensed under MIT License: http://www.opensource.org/licenses/MIT  */
package com.barrybecker4.puzzle.shuffle;

/**
 * @author Barry Becker
 */
public class Deck {

    private int[] data_;

    public Deck(int nCards) {
        data_ = new int[nCards];
        for (int i = 0; i < nCards; i++) {
            data_[i] = i;
        }
    }

    public int get(int i) {
        return data_[i];
    }

    public boolean isSorted() {
        if (data_ == null || data_.length == 1) {
            return true;
        }
        for (int i = 1; i < data_.length; i++) {
            if (data_[i] != data_[i-1] + 1)  {
                return false;
            }
        }
        return true;
    }

    public void doPerfectShuffle(int iCut) {

        int size = data_.length;
        int[] temp = new int[size];
        int loop = Math.min(iCut, size - iCut);
        int ct = size - 1;
        for (int i = 0; i < loop; i++) {
            temp[ct--] = data_[iCut - 1 - i];
            temp[ct--] = data_[size - 1 - i];
        }
        if (iCut < size - iCut) {
            for (int i = size - iCut - 1;  i >= iCut; i--) {
               temp[ct--] = data_[i];
            }
        }
        else {
           for (int i = iCut - 1; i >= size - iCut; i--) {
               temp[ct--] = data_[i];
            }
        }
        assert (ct == -1): "ct = " + ct;
        data_ = temp;    // replace our data with the result of the shuffle
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("Deck: ");

        for (int i=0; i<data_.length-1; i++) {
            buf.append(data_[i]+", ");
        }
        buf.append(data_[data_.length - 1]);
        return buf.toString();
    }
}
