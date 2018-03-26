package com.khallware.activi.bag;

public interface EventHandler<T extends Event>
{
	public void handle(T event);
}
