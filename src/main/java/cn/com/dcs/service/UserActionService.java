package cn.com.dcs.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.dcs.dao.UserActionMapper;
import cn.com.dcs.framework.base.constant.EActionUserType;
import cn.com.dcs.framework.tree.DefaultTreeNode;
import cn.com.dcs.framework.tree.DefaultTreeNode.PropertySetter;
import cn.com.dcs.framework.tree.MenuTreeNode;
import cn.com.dcs.model.UserAction;

/**
 * 用户权限服务类
 * 
 * @author shishb
 * @version 1.0
 */
@Service
public class UserActionService {
	@Resource
	private UserActionMapper userActionMapper;

	/**
	 * 系统用户登录时，获得菜单树
	 * 
	 * @param user
	 * @return
	 */
	public MenuTreeNode getMenuTree() {
		return findTreeByUser(MenuTreeNode.class, new ActionPropertySetter<MenuTreeNode>() {
			public void set(MenuTreeNode node, UserAction entity) {
				if (entity != null) {
					if ("#".equals(entity.getUri())) {
						node.uri = entity.getUri();
					} else {
						node.setUri(entity.getUri());
					}
					node.iconSkin = entity.getIconSkin();
					if (null == node.iconSkin) {
						node.iconSkin = "";
					}
				}
			}
		});
	}

	/**
	 * 把权限转换为对应的权限树
	 * 
	 * @param ct
	 * @param user
	 * @param setter
	 * @return
	 */
	public <T extends DefaultTreeNode> T findTreeByUser(Class<T> ct, final ActionPropertySetter<T> setter) {
		List<UserAction> userActionList = userActionMapper.findByType(EActionUserType.Crawler.ordinal());
		final boolean allAction = true;
		T treeNode = DefaultTreeNode.parseTree(ct, userActionList, new PropertySetter<T, UserAction>() {
			public void setProperty(T node, UserAction entity) {
				if (allAction) {
					node.checked = true;
				}
				setter.set(node, entity);
			}
		});
		return treeNode;
	}

	/**
	 * 属性容器
	 */
	public interface ActionPropertySetter<K extends DefaultTreeNode> {
		void set(K k, UserAction entity);
	}
}
