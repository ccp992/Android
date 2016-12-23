package com.cuny.qc.chenc.wordguess;

import java.util.ArrayList;
import java.util.HashMap;


public class TrieNode {
    private HashMap<Character, TrieNode> children;
    private String word;
    private ArrayList<TrieNode> array = new ArrayList();

    public TrieNode() {
        children = new HashMap<>();
        word = null;
    }

    public void add(String s) {
        TrieNode currentNode = this;
        for (char c:s.toCharArray()
             ) {
            if (!currentNode.getChildren().containsKey(c)){
                TrieNode n = new TrieNode();
                currentNode.getChildren().put(c,n);
            }
            currentNode = currentNode.getChildren().get(c);
        }
        currentNode.setWord(s);
    }

    public String getAnyWordStartingWith(String s) {
        TrieNode currentNode = this;
        if (s == null){
            return findWord(this);
        }
        if (children.get(s.charAt(0)) == null) return null;
        for (char c:s.toCharArray()
             ) {
            if (!currentNode.getChildren().containsKey(c)) {
                return null;
            }
            currentNode = currentNode.getChildren().get(c);
        }
        return findWord(currentNode);
    }

    public HashMap<Character, TrieNode> getChildren(){
        return children;
    }

    public void setWord(String word){
        this.word = word;
    }

    public String getWord(){
        return this.word;
    }

    public String findWord(TrieNode TN){
        array.clear();
        if (TN.getChildren().size() == 0 || TN.getWord() != null){
            return TN.getWord();
        }
        array.addAll(TN.getChildren().values());
        return findWord(array.get((int) (Math.random() * array.size())));

    }
}
