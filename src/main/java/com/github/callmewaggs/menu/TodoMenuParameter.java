package com.github.callmewaggs.menu;

import java.util.ArrayList;
import java.util.List;

public class TodoMenuParameter {

  private TodoMenu menu;
  private Long id;
  private String content;
  private List<Long> parentIds;

  /** menu, id, content, parentIds setter */
  public TodoMenuParameter(TodoMenu menu, Long id, String content, List<Long> parentIds) {
    this.menu = menu;
    this.id = id;
    this.content = content;
    this.parentIds = parentIds;
  }

  /** 사용자가 입력한 메뉴 저장 후 각 번호에 맞는 행동으로 return */
  public static TodoMenuParameter parse(String input) {
    String[] parsed = input.split(" ");
    
    /** menu에 사용자가 입력한 메뉴 번호 저장 */
    TodoMenu menu = TodoMenu.fromMenuNumber(parsed[0]);
    
    switch (menu) {
      case QUIT:
        return quit();
      case SHOW_LIST:
        return showList();
      case CREATE:
        return create(parsed);
      case UPDATE:
        return update(parsed);
      case REMOVE:
        return remove(parsed);
      case FINISH:
        return finish(parsed);
      case SEARCH:
        return search(parsed);        
      default:
        throw new IllegalStateException("Wrong menu. try again.");
    }
  }
  
  private static TodoMenuParameter search(String[] parsed) {
    String content = parsed[1];
	return new TodoMenuParameter(TodoMenu.SEARCH, null, content, null);
  }
  
  private static TodoMenuParameter finish(String[] parsed) {
    long id = Long.parseLong(parsed[1]);
    return new TodoMenuParameter(TodoMenu.FINISH, id, null, null);
  }

  private static TodoMenuParameter remove(String[] parsed) {
    long id = Long.parseLong(parsed[1]);
    return new TodoMenuParameter(TodoMenu.REMOVE, id, null, null);
  }

  private static TodoMenuParameter update(String[] parsed) {
    long id = Long.parseLong(parsed[1]);
    String content = parsed[2];
    List<Long> parentIds = getParentIds(parsed, 3);
    return new TodoMenuParameter(TodoMenu.UPDATE, id, content, parentIds);
  }

  private static TodoMenuParameter create(String[] parsed) {
    String content = parsed[1];
    List<Long> parentIds = getParentIds(parsed, 2);
    return new TodoMenuParameter(TodoMenu.CREATE, null, content, parentIds);
  }

  private static TodoMenuParameter showList() {
    return new TodoMenuParameter(TodoMenu.SHOW_LIST, null, null, null);
  }

  private static TodoMenuParameter quit() {
    return new TodoMenuParameter(TodoMenu.QUIT, null, null, null);
  }

  /** parentIds parser */
  private static List<Long> getParentIds(String[] parsed, int threshold) {
    List<Long> parents = new ArrayList<>();
    if (parsed.length > threshold) {
      for (int i = threshold; i < parsed.length; ++i) {
        parents.add(Long.parseLong(parsed[i].substring(1)));
      }
    }
    return parents;
  }

  /** menu getter */
  public TodoMenu getMenu() {
    return menu;
  }

  /** id getter */
  public Long getId() {
    return id;
  }
  
  /** content getter */
  public String getContent() {
    return content;
  }
  
  /** parentIds getter */
  public List<Long> getParentIds() {
    return parentIds;
  }
}
