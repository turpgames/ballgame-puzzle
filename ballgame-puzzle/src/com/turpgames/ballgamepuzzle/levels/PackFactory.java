package com.turpgames.ballgamepuzzle.levels;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Node;

import com.turpgames.ballgamepuzzle.collisionhandlers.BallCollisionHandlerChain;
import com.turpgames.ballgamepuzzle.collisionhandlers.BounceHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.EnemyHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.HoleHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.PortalHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.PurpleHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.RedGrayHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.StarHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.StoneHandler;
import com.turpgames.ballgamepuzzle.collisionhandlers.TargetHandler;
import com.turpgames.ballgamepuzzle.effects.meta.CircularTripEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.IEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.PathTripEffectMeta;
import com.turpgames.ballgamepuzzle.effects.meta.RollingEffectMeta;
import com.turpgames.ballgamepuzzle.objects.Ball;
import com.turpgames.framework.v0.util.Color;
import com.turpgames.framework.v0.util.Game;
import com.turpgames.utils.Util;

public class PackFactory {
	public static LevelMeta getDesignerLevel() {
		try {
			String filePath = "/Users/mehmet/Documents/code/github/turpgames/ballgame-puzzle/ballgame-puzzle-android/assets/packs/green-pack.xml";
			InputStream is = new FileInputStream(filePath);
			Node root = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is).getDocumentElement();
			LevelPack pack = create(root);

			for (LevelMeta level : pack.getLevels()) {
				if (level.isDesignerLevel()) {
					return level;
				}
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		System.out.println("No designer level!!!");
		return null;
	}

	public static LevelPack greenPack() {
		LevelPack pack = create("packs/green-pack.xml");
		LevelMeta level1 = pack.getLevels()[0];

		if (level1.getState() == LevelMeta.Locked)
			level1.setState(LevelMeta.Unlocked);

		return pack;
	}

	private static LevelPack create(Node root) {
		LevelPack pack = new LevelPack();

		pack.setTitle(getAttr(root, "title"));
		pack.setThemeColor(Color.fromHex(getAttr(root, "color")));
		pack.setLevels(createLevels(root, pack));

		return pack;
	}

	private static LevelPack create(String path) {
		InputStream is = Game.getResourceManager().readFile(path);
		Node root = Util.Xml.loadXml(is).getDocumentElement();

		return create(root);
	}

	private static LevelMeta[] createLevels(Node root, LevelPack pack) {
		List<LevelMeta> levels = new ArrayList<LevelMeta>();

		int i = 1;
		for (Node levelNode : getNodes(root, "level")) {
			levels.add(createLevel(levelNode, i++, pack));
		}

		return levels.toArray(new LevelMeta[0]);
	}

	private static LevelMeta createLevel(Node levelNode, int index, LevelPack pack) {
		LevelMeta level = new LevelMeta();

		level.setDesignerLevel(Util.Strings.parseBoolean(getAttr(levelNode, "designer"), false));
		level.setIndex(index);
		level.setPack(pack);
		level.setId(pack.getTitle() + index);
		level.setBalls(createBalls(getNodes(levelNode, "ball")));
		level.setBlocks(createBlocks(getNodes(levelNode, "block")));

		buildContactListeners(level);

		return level;
	}

	private static BallMeta[] createBalls(List<Node> ballNodes) {
		List<BallMeta> balls = new ArrayList<BallMeta>();
		for (Node ballNode : ballNodes) {
			balls.add(createBall(ballNode));
		}
		return balls.toArray(new BallMeta[0]);
	}

	private static BlockMeta[] createBlocks(List<Node> blockNodes) {
		List<BlockMeta> blocks = new ArrayList<BlockMeta>();
		for (Node blockNode : blockNodes) {
			blocks.add(createBlock(blockNode));
		}
		return blocks.toArray(new BlockMeta[0]);
	}

	private static void buildContactListeners(LevelMeta level) {
		Set<Integer> builtTypes = new HashSet<Integer>();

		BallCollisionHandlerChain chain = new BallCollisionHandlerChain(
				new TargetHandler(),
				new StarHandler());

		for (BallMeta ballMeta : level.getBalls()) {
			int type = ballMeta.getType();

			if (builtTypes.contains(type))
				continue;
			builtTypes.add(type);

			if (type == Ball.Stone) {
				chain.add(new StoneHandler());
			}
			else if (type == Ball.Bounce) {
				chain.add(new BounceHandler());
			}
			else if (type == Ball.Portal) {
				chain.add(new PortalHandler());
			}
			else if (type == Ball.Enemy) {
				chain.add(new EnemyHandler());
			}
			else if (type == Ball.RedGray) {
				chain.add(new RedGrayHandler());
			}
			else if (type == Ball.BlackHole ||
					type == Ball.WhiteHole) {
				chain.add(new HoleHandler(type));
			}
			else if (type == Ball.Purple) {
				chain.add(new PurpleHandler());
			}
		}

		level.setContactListener(new BallContactListener(chain));
	}

	private static BallMeta createBall(Node ballNode) {
		BallMeta ballMeta = new BallMeta(
				getBallType(getAttr(ballNode, "type")),
				getBallSize(getAttr(ballNode, "size")),
				Util.Strings.parseFloat(getAttr(ballNode, "x")),
				Util.Strings.parseFloat(getAttr(ballNode, "y")));

		ballMeta.setEffect(createEffects(ballNode));

		return ballMeta;
	}

	private static BlockMeta createBlock(Node blockNode) {
		return new BlockMeta(
				Util.Strings.parseFloat(getAttr(blockNode, "x")),
				Util.Strings.parseFloat(getAttr(blockNode, "y")),
				Util.Strings.parseFloat(getAttr(blockNode, "width")),
				Util.Strings.parseFloat(getAttr(blockNode, "height")),
				Util.Strings.parseFloat(getAttr(blockNode, "rotation")));
	}

	private static IEffectMeta[] createEffects(Node ballNode) {
		List<IEffectMeta> effects = new ArrayList<IEffectMeta>();

		List<Node> effectNodes = getNodes(ballNode, "effect");
		for (Node effectNode : effectNodes) {
			effects.add(createEffect(effectNode));
		}

		return effects.toArray(new IEffectMeta[0]);
	}

	private static IEffectMeta createEffect(Node effectNode) {
		String type = getAttr(effectNode, "type");

		if (type.equals("Rolling")) {
			RollingEffectMeta meta = new RollingEffectMeta();

			meta.setClockWise(Util.Strings.parseBoolean(getAttr(effectNode, "clockwise")));
			meta.setTotalDuration(Util.Strings.parseFloat(getAttr(effectNode, "duration")));

			return meta;
		}
		if (type.equals("CircularTrip")) {
			CircularTripEffectMeta meta = new CircularTripEffectMeta();

			meta.setClockWise(Util.Strings.parseBoolean(getAttr(effectNode, "clockwise")));
			meta.setTotalDuration(Util.Strings.parseFloat(getAttr(effectNode, "duration")));
			meta.setCenter(
					Util.Strings.parseFloat(getAttr(effectNode, "x")),
					Util.Strings.parseFloat(getAttr(effectNode, "y")));

			return meta;
		}
		if (type.equals("PathTrip")) {
			PathTripEffectMeta meta = new PathTripEffectMeta();

			meta.setRoundTrip(Util.Strings.parseBoolean(getAttr(effectNode, "roundtrip")));
			meta.setTotalDuration(Util.Strings.parseFloat(getAttr(effectNode, "duration")));

			String path = getAttr(effectNode, "path");
			String[] nodes = path.split(",");

			if (nodes.length % 2 != 0) {
				throw new UnsupportedOperationException("Node length must be even!");
			}

			for (int i = 0; i < nodes.length; i += 2) {
				meta.addNode(
						Util.Strings.parseFloat(nodes[i]),
						Util.Strings.parseFloat(nodes[i + 1]));
			}

			return meta;
		}

		throw new UnsupportedOperationException("Unknown effect type: " + type);
	}

	private static String getAttr(Node node, String attr) {
		return Util.Xml.getAttributeValue(node, attr);
	}

	private static List<Node> getNodes(Node node, String nodeName) {
		return Util.Xml.getChildNodes(node, nodeName);
	}

	private static int getBallType(String type) {
		if (type.equals("Blue"))
			return Ball.Blue;
		if (type.equals("Target"))
			return Ball.Target;
		if (type.equals("Enemy"))
			return Ball.Enemy;
		if (type.equals("Stone"))
			return Ball.Stone;
		if (type.equals("Bounce"))
			return Ball.Bounce;
		if (type.equals("Purple"))
			return Ball.Purple;
		if (type.equals("Subject"))
			return Ball.Subject;
		if (type.equals("Portal"))
			return Ball.Portal;
		if (type.equals("RedGray"))
			return Ball.RedGray;
		if (type.equals("BlackHole"))
			return Ball.BlackHole;
		if (type.equals("WhiteHole"))
			return Ball.WhiteHole;
		if (type.equals("Star"))
			return Ball.Star;
		throw new UnsupportedOperationException("Unknown ball type: " + type);
	}

	private static float getBallSize(String size) {
		if (size.equals("s"))
			return Ball.Small;
		if (size.equals("m"))
			return Ball.Medium;
		if (size.equals("l"))
			return Ball.Large;

		throw new UnsupportedOperationException("Unknown ball size: " + size);
	}
}
