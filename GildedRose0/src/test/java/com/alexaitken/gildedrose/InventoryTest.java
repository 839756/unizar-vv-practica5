package com.alexaitken.gildedrose;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alexaitken.gildedrose.Inventory;
import com.alexaitken.gildedrose.Item;


public class InventoryTest {

    private Inventory createInventory(Item... items) {
        return new Inventory(items);
    }

    @Test
    public void should_never_changes_quailty_of_Sulfuras() {
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        Inventory inventory = createInventory(sulfuras);
        inventory.updateQuality();
        assertEquals(80, sulfuras.getQuality());
    }

    @Test
    public void should_never_changes_sellIn_of_Sulfuras() {
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", 0, 80);
        Inventory inventory = createInventory(sulfuras);
        inventory.updateQuality();
        assertEquals(0, sulfuras.getSellIn());
    }

    @Test
    public void should_never_changes_quailty_of_Sulfuras_sellin_negative() {
        Item sulfuras = new Item("Sulfuras, Hand of Ragnaros", -1, 80);
        Inventory inventory = createInventory(sulfuras);
        inventory.updateQuality();
        assertEquals(80, sulfuras.getQuality());
    }

    @Test
    public void should_lower_the_sellIn_by_one_for_normal_items() {
        Item normalItem = new Item("+5 Dexterity Vest", 10, 20);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(9, normalItem.getSellIn());
    }

    @Test
    public void should_lower_the_quality_by_one_for_normal_items() {
        Item normalItem = new Item("+5 Dexterity Vest", 10, 20);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(19, normalItem.getQuality());
    }

    @Test
    public void should_not_lower_the_quality_below_zero() {
        Item normalItem = new Item("+5 Dexterity Vest", 10, 0);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(0, normalItem.getQuality());
    }

    @Test
    public void should_lower_the_quality_twice_as_fast_once_the_sell_in_date_has_passed() {
        Item normalItem = new Item("+5 Dexterity Vest", -1, 25);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(23, normalItem.getQuality());
    }

    @Test
    public void should_lower_the_quality_by_one_for_special_items_limit() {
        Item normalItem = new Item("+5 Dexterity Vest", -8, 1);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(0, normalItem.getQuality());
    }

    @Test
    public void should_not_lower_the_quality_below_zero_for_special_items() {
        Item normalItem = new Item("+5 Dexterity Vest", -9, 0);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(0, normalItem.getQuality());
    }

    @Test
    public void should_lower_the_quality_twice_as_fast_limit (){
        Item normalItem = new Item("+5 Dexterity Vest", 0, 25);
        Inventory inventory = createInventory(normalItem);
        inventory.updateQuality();
        assertEquals(23, normalItem.getQuality());
    }

    @Test
    public void should_increase_the_quality_of_aged_brie_as_it_gets_older() {
        Item agedBrie = new Item("Aged Brie", 10, 25);
        Inventory inventory = createInventory(agedBrie);
        inventory.updateQuality();
        assertEquals(26, agedBrie.getQuality());
    }

    @Test
    public void should_not_increase_the_quality_of_aged_brie_over_50() {
        Item agedBrie = new Item("Aged Brie", 10, 50);
        Inventory inventory = createInventory(agedBrie);
        inventory.updateQuality();
        assertEquals(50, agedBrie.getQuality());
    }

    @Test
    public void should_increase_the_quality_twice_as_fast_once_sellin_negative() {
        Item agedBrie1 = new Item("Aged Brie", -8, 25);
        Item agedBrie2 = new Item("Aged Brie", -8, 50);
        Item agedBrie3 = new Item("Aged Brie", -8, 49);
        Inventory inventory = createInventory(agedBrie1, agedBrie2, agedBrie3);
        inventory.updateQuality();
        assertEquals(27, agedBrie1.getQuality());
        assertEquals(50, agedBrie2.getQuality());
        assertEquals(50, agedBrie3.getQuality());
    }

    @Test
    public void should_increase_the_quality_twice_as_fast_once_sellin_negative_limit() {
        Item agedBrie = new Item("Aged Brie", 0, 25);
        Inventory inventory = createInventory(agedBrie);
        inventory.updateQuality();
        assertEquals(27, agedBrie.getQuality());
    }

    @Test
    public void should_lower_backstage_passes_to_zero_quality_once_concert_has_happened() {
        Item backStagePass = new Item("Backstage passes to a TAFKAL80ETC concert", -1, 20);
        Inventory inventory = createInventory(backStagePass);
        inventory.updateQuality();
        assertEquals(0, backStagePass.getQuality());
    }

    @Test
    public void should_increase_backstage_passes_quality_by_1_when_the_concert_is_more_than_10_days_away() {
        Item backStagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 11, 20);
        Inventory inventory = createInventory(backStagePass);
        inventory.updateQuality();
        assertEquals(21, backStagePass.getQuality());
    }

    @Test
    public void should_increase_backstage_passes_quality_by_2_when_the_concert_is_10_days_or_less_away() {
        Item backStagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 27);
        Inventory inventory = createInventory(backStagePass);
        inventory.updateQuality();
        assertEquals(29, backStagePass.getQuality());
    }

    @Test
    public void should_increase_backstage_passes_quality_by_3_when_the_concert_is_5_days_or_less_away() {
        Item backStagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 44);
        Inventory inventory = createInventory(backStagePass);
        inventory.updateQuality();
        assertEquals(47, backStagePass.getQuality());
    }

    @Test
    public void should_not_increase_backstage_passes_above_a_quality_of_50() {
        Item backStagePassMoreThan10DaysAway = new Item("Backstage passes to a TAFKAL80ETC concert", 15, 50);
        Item backStagePass10DaysAway = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49);
        Item backStagePass5DaysAway = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 48);
        Inventory inventory = createInventory(backStagePassMoreThan10DaysAway, backStagePass10DaysAway, backStagePass5DaysAway);
        inventory.updateQuality();
        assertEquals(50, backStagePassMoreThan10DaysAway.getQuality());
        assertEquals(50, backStagePass10DaysAway.getQuality());
        assertEquals(50, backStagePass5DaysAway.getQuality());
    }

    @Test
    public void should_be_quality_50() {
        Item backStagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 50);
        Item backStagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", 6, 50);
        Item backStagePass3 = new Item("Backstage passes to a TAFKAL80ETC concert", 6, 49);
        Item backStagePass4 = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 50);
        Item backStagePass5 = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49);
        Item backStagePass6 = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 50);
        Item backStagePass7 = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 49);
        Item backStagePass8 = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 48);
        
        Inventory inventory = createInventory(backStagePass1, backStagePass2, backStagePass3, backStagePass4, backStagePass5, backStagePass6, backStagePass7, backStagePass8);

        inventory.updateQuality();
        
        assertEquals(50, backStagePass1.getQuality());
        assertEquals(50, backStagePass2.getQuality());
        assertEquals(50, backStagePass3.getQuality()); 
        assertEquals(50, backStagePass4.getQuality());
        assertEquals(50, backStagePass5.getQuality());
        assertEquals(50, backStagePass6.getQuality());
        assertEquals(50, backStagePass7.getQuality());   
        assertEquals(50, backStagePass8.getQuality());
    }

    @Test
    public void should_increase_backstage_passes_quality_twice_as_fast_limit() {
        Item backStagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 6, 25);
        Inventory inventory = createInventory(backStagePass);
    
        inventory.updateQuality();
        
        assertEquals(27, backStagePass.getQuality());
    }

    @Test
    public void should_increase_backstage_passes_quality_three_times_as_fast_limit() {
        Item backStagePass = new Item("Backstage passes to a TAFKAL80ETC concert", 1, 25);
        Inventory inventory = createInventory(backStagePass);
    
        inventory.updateQuality();
        
        assertEquals(28, backStagePass.getQuality());
    }

    @Test
    public void should_be_quality_0() {
        Item backStagePass1 = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 25);
        Item backStagePass2 = new Item("Backstage passes to a TAFKAL80ETC concert", -8, 0);

        Inventory inventory = createInventory(backStagePass1, backStagePass2);

        inventory.updateQuality();
        
        assertEquals(0, backStagePass1.getQuality());
        assertEquals(0, backStagePass2.getQuality());
    }
}
