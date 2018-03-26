package com.khallware.activi.bag;

import java.util.List;
import java.util.ArrayList;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class BrownstonePark implements Park, EventHandler<SeasonEndEvent>
{
	private static final Logger logger = LoggerFactory.getLogger(
		BrownstonePark.class);
	private static List<Section> sections = new ArrayList<>();

	private Ranger ranger = new BossRanger(this);
	private Item item = Item.BAG;
	private List<Bear> bears = new ArrayList<>();
	private HumphreyBear humphrey = new HumphreyBear();
	private SmokeyBear smokey = new SmokeyBear();

	public static enum Item {
		BAG, CLIFF, STUMP, TWIG, BUSH, RABBITHOLE, GEYSER
	};

	public BrownstonePark(List<Bear> bears)
	{
		this.bears = (bears != null) ? bears : this.bears;
	}

	@Override
	public String getName()
	{
		return("Brownstone National Park");
	}

	@Override
	public List<Section> getSections()
	{
		return(sections);
	}

	@Override
	public void handle(SeasonEndEvent event)
	{
		sections.add(new DefaultSection(event));
		sections.add(new DefaultSection(event));
		sections.add(new DefaultSection(event));
		sections.add(new DefaultSection(event));
		logger.info("The Tourists have departed {} where {} lives, "
			+"leaving litter everywhere.",
			this.getName(), humphrey.getName());
	}

	@Override
	public List<Bear> getBears()
	{
		return(bears);
	}

	public void setFocus(Item item)
	{
		this.item = item;
	}
}
