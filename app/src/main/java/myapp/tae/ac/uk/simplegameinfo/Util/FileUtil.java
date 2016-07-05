package myapp.tae.ac.uk.simplegameinfo.Util;

import android.app.Activity;
import android.content.SharedPreferences;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import myapp.tae.ac.uk.simplegameinfo.CacheManager.FileMetaInfo;
import myapp.tae.ac.uk.simplegameinfo.Constants.Constants;
import myapp.tae.ac.uk.simplegameinfo.Model.Data;

/**
 * Created by Karma on 09/06/16.
 */
public class FileUtil {
    public static void writeFile(Activity context, String path, String data){
        try {
            FileOutputStream fos = context.openFileOutput(path, context.MODE_PRIVATE);
            OutputStreamWriter outWriter = new OutputStreamWriter(fos);
            outWriter.write(data);
            outWriter.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(Activity context, String path){
        try {
            FileInputStream fis = context.openFileInput(Constants.DATA_FILE_PATH);
            InputStreamReader isReader = new InputStreamReader(fis);
            char[] inputBuffer = new char[128];
            String data = "";
            int charRead;
            while ((charRead = isReader.read(inputBuffer))>0){
                String readString=String.copyValueOf(inputBuffer,0,charRead);
                data +=readString;
            }
            isReader.close();
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<Data> convertFileDataTextToList(String data){
        List<Data> listData = new ArrayList<>();
        String[] arrayGames = data.split(Constants.GAME_SEPARATOR);
        Data gameData;
        for (int i = 0; i < arrayGames.length; i++) {
            String game = arrayGames[i];
            String[] arrayGameData = game.split(Constants.GAME_DATA_SEPARATOR_REGEX);
            gameData = new Data();
            gameData.setName(arrayGameData[0]);
            gameData.setJackpot(Integer.parseInt(arrayGameData[1]));
            gameData.setDate(arrayGameData[2]);
            listData.add(gameData);
        }
        return listData;
    }

    public static String convertListToFileDataText(List<Data> listData){
        String fileData = "";
        for (int i = 0; i < listData.size(); i++) {
            fileData+=listData.get(i).getName()+Constants.GAME_DATA_SEPARATOR;
            fileData+=listData.get(i).getJackpot().toString()+Constants.GAME_DATA_SEPARATOR;
            fileData+=listData.get(i).getDate();
            if(i!=listData.size()-1)
                fileData+=Constants.GAME_SEPARATOR;
        }
        return fileData;
    }

    public static long getFileSize(File f) {
        long size = 0;

        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                size += getFileSize(file);
            }
        } else {
            size=f.length();
        }
        return size;
    }

    public static FileMetaInfo storeFileMetaInfo(File f){
        if(!f.exists())
            return null;
        FileMetaInfo fileMetaInfo = new FileMetaInfo();
        fileMetaInfo.setAbsolutePath(f.getAbsolutePath());
        fileMetaInfo.setLastModified(f.lastModified());
        fileMetaInfo.setSize(getFileSize(f));
        return fileMetaInfo;
    }

    public static List<FileMetaInfo> getFileMetaInfoListFromDirectory(File directory){
        if (!directory.exists()&&!directory.isDirectory())
            return null;
        List<FileMetaInfo> fileMetaInfoList = new ArrayList<>();
        File[] dirContent = directory.listFiles();
        for (File f:dirContent) {
            fileMetaInfoList.add(storeFileMetaInfo(f));
        }
        return fileMetaInfoList;
    }

    public static LinkedList<FileMetaInfo> sortByFilesOldestToNewest(List<FileMetaInfo> fileMetaInfoList){

        FileMetaInfo[] fileMetaInArray = new FileMetaInfo[fileMetaInfoList.size()];
        fileMetaInArray = fileMetaInfoList.toArray(fileMetaInArray);
        Arrays.sort(fileMetaInArray);
        LinkedList<FileMetaInfo> oldToNewSortedList = new LinkedList<>();
        for (int i = 0; i <fileMetaInArray.length; i++) {
            oldToNewSortedList.add(fileMetaInArray[i]);
        }
        return oldToNewSortedList;
    }

    public static List<String> convertSortedFileMetaToStringList(LinkedList<FileMetaInfo> sorted){
        List<String> sortedFileMetaInfoInString = new ArrayList<>();
        for (int i = 0; i < sorted.size(); i++) {
            sortedFileMetaInfoInString.add(sorted.get(i).toString());
        }
        return sortedFileMetaInfoInString;
    }

    public static LinkedList<FileMetaInfo> covertSortedStringToLList(ArrayList<String> sorted){
        LinkedList<FileMetaInfo> oldToNewSortedList = new LinkedList<>();
        String metaData[];
        String absolutePath;
        long date;
        long size;
        for (int i = 0; i < sorted.size(); i++) {
            String fileMeta = sorted.get(i);
            FileMetaInfo fileMetaInfo = new FileMetaInfo();
            metaData = fileMeta.split(",");
            absolutePath = metaData[0];
            date = Long.parseLong(metaData[1]);
            size = Long.parseLong(metaData[2]);
            fileMetaInfo.setAbsolutePath(absolutePath);
            fileMetaInfo.setLastModified(date);
            fileMetaInfo.setSize(size);
            oldToNewSortedList.add(fileMetaInfo);
        }
        return oldToNewSortedList;
    }

    public static long getTotalUsedCacheSize(LinkedList<FileMetaInfo> sorted){
        long totalSize = 0;
        for (int i = 0; i < sorted.size(); i++) {
            totalSize+=sorted.get(i).getSize();
        }
        return totalSize;
    }

    public static int calculateFileRemoveCount(LinkedList<FileMetaInfo> sorted, long newFileSize){
        if(newFileSize<=0)
            return 0;
        int removeFileCount = 0;
        long removeFileSize = 0;
        for (int i = 0; i < sorted.size(); i++) {
            removeFileSize+=sorted.get(i).getSize();
            removeFileCount++;
            if(removeFileSize>=newFileSize)
                break;
        }
        return removeFileCount;
    }

    public static List<FileMetaInfo> getFilesToRemove(LinkedList<FileMetaInfo> sorted, int fileRemoveCount) {
        int removeCounter = fileRemoveCount<sorted.size()?fileRemoveCount:sorted.size();
        List<FileMetaInfo> fileToRemove = new ArrayList<>();
        for (int i = 0; i < removeCounter; i++) {
            fileToRemove.add(sorted.get(i));
        }
        return fileToRemove;
    }

    private static LinkedList<FileMetaInfo> removeFileMetaInfo(LinkedList<FileMetaInfo> sorted, int fileRemoveCount) {
        int removeCounter = fileRemoveCount<sorted.size()?fileRemoveCount:sorted.size();
        for (int i = 0; i < removeCounter; i++) {
            sorted.removeFirst();
        }
        return sorted;
    }

    public static void removeFiles(List<FileMetaInfo> fileToRemove) {
        for (int i = 0; i < fileToRemove.size(); i++) {
            new File(fileToRemove.get(i).getAbsolutePath()).delete();
        }
    }

    public static void addFileToDir(File newFile, File dir) {

    }

    public static LinkedList<FileMetaInfo> addFileToCache(File newFile, File dir,
                                                              LinkedList<FileMetaInfo> sorted, long maxSize ){
        long usedSize = getTotalUsedCacheSize(sorted);
        long dif = maxSize-usedSize;
        long newFileSize = getFileSize(newFile);
//        if(dif< newFileSize){
//            int fileRemoveCount = calculateFileRemoveCount(sorted,newFileSize);
//            List<FileMetaInfo> fileToRemove= getFilesToRemove(sorted, fileRemoveCount);
//            sorted = removeFileMetaInfo(sorted, fileRemoveCount);
//            removeFiles(fileToRemove);
//        }

        addFileToDir(newFile, dir);
        sorted.add(storeFileMetaInfo(newFile));
        return sorted;
        }

    public static boolean hasReachMaxLimit(LinkedList<FileMetaInfo> sorted, long maxLimit){
        long currentUsedUp = getTotalUsedCacheSize(sorted);
        return maxLimit <= currentUsedUp;
    }

    public static LinkedList<FileMetaInfo> checkCacheSizeAndManage(LinkedList<FileMetaInfo> sorted, long maxLimit){
        if(hasReachMaxLimit(sorted, maxLimit)){
            FileMetaInfo removedFile = sorted.removeFirst();
            new File(removedFile.getAbsolutePath()).delete();
            sorted = checkCacheSizeAndManage(sorted, maxLimit);
        }
        return sorted;
    }

    /**
     * Use this class to convert root directory to linked list of FileMetaInfo and sort remove oldest
     * files if max dir file size is reached or crossed. This method is used when you create Link list
     * of contents of root dir for the first time. However, it is not recommended to use the method for future
     * management of directory it uses CPU and takes time to complete the task. Since you have link list of
     * FileMetaInfo afterward, you can use checkCacheSizeAndManage(LinkedList<FileMetaInfo> sorted, long maxLimit)
     * method to manage contents of your root dir.
     * @param dir
     * @param maxLimit
     * @return
     */
    public static LinkedList<FileMetaInfo> processAndManageRootDir(File dir, long maxLimit){
        List<FileMetaInfo> dirContentFileMetaList = getFileMetaInfoListFromDirectory(dir);
        LinkedList<FileMetaInfo> sorted = sortByFilesOldestToNewest(dirContentFileMetaList);
        sorted = checkCacheSizeAndManage(sorted, maxLimit);
        return sorted;
    }

    public static void storeFileMetaListToSharePreferences(SharedPreferences s, LinkedList<FileMetaInfo> sorted){
        List<String> sortedInString = convertSortedFileMetaToStringList(sorted);
        Set sortFileMetaSet = new HashSet(sortedInString);
        SharedPreferences.Editor editor = s.edit();
        editor.putStringSet("SORTED_FILE_META_SET",sortFileMetaSet);
        editor.commit();
    }

    public static LinkedList<FileMetaInfo> getSortedFileMetaFromSharePref(SharedPreferences s){
        Set<String> sortedFileMetaSet = s.getStringSet("SORTED_FILE_META_SET", null);
        if(sortedFileMetaSet==null)
            return null;
        LinkedList<FileMetaInfo> sorted = covertSortedStringToLList(new ArrayList<String>(sortedFileMetaSet));
        return sorted;
    }
}
