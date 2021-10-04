package CanTuna.CanTunaacon3d.Service;

import CanTuna.CanTunaacon3d.domain.ExRate;
import CanTuna.CanTunaacon3d.domain.Item;
import CanTuna.CanTunaacon3d.domain.User;
import CanTuna.CanTunaacon3d.repository.ExRateRepository;
import CanTuna.CanTunaacon3d.repository.ItemRepository;
import CanTuna.CanTunaacon3d.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class ItemService {

    private final ItemRepository itemRepository;
    private final ExRateRepository exRateRepository;


    private final Long USER_TYPE_CREATOR = 0L;
    private final Long USER_TYPE_EDITOR = 1L;
    private final Long USER_TYPE_CUSTOMER = 2L;
/*
    private final Integer CURRENCY_TYPE_KOR = 0;
    private final Integer CURRENCY_TYPE_ENG = 1;
    private final Integer CURRENCY_TYPE_CHN = 2;
*/

    @Autowired
    public ItemService(ItemRepository itemRepository, ExRateRepository exRateRepository) {
        this.exRateRepository = exRateRepository;
        this.itemRepository = itemRepository;
    }

    public Item createItem(Item item) {

        Item result = itemRepository.createItem(item);

        return result;
    }

    public Optional<Item> updateItem(Long targetId, Item updateItem) {
        // 에디터 권한 조회
        //authEditor(editUser);

        Optional<Item> result = itemRepository.editItem(targetId, updateItem);

        return result;
    }

    public  Optional<Item> findItemByName(String itemName) {
        Optional<Item> findItem = itemRepository.findItemByName(itemName);
        Item temp = findItem.get();
        System.out.println("-----------------------");
        System.out.println("Serviceitemfind id ::: " +  temp.getItemId());
        System.out.println("Serviceitemfind namekor ::: " +  temp.getItemNameKor());
        System.out.println("Serviceitemfind creator ::: " +  temp.getItemCreator() );
        System.out.println("Serviceitemfind approved ::: " +  temp.getItemApproved());
        System.out.println("Serviceitemfind approved ::: " +  temp.getItemTextKor());
        System.out.println("-----------------------");
        return findItem.stream().findAny();
    }

    public  Optional<Item> findItemById(Long itemId) {
        Optional<Item> findItem = itemRepository.findItemById(itemId);
        return findItem.stream().findAny();
    }

    public List<Item> viewItem() {
        List<Item>itemList = itemRepository.findAllItem();
        return itemList;
    }
    public List<Item> viewApprovedItem() {
        List<Item>itemList = itemRepository.findApprovedItem();
        return itemList;
    }

    public List<Item> nonApprovedItem() {
        List<Item>itemList = itemRepository.findNonApprovedItem();
        return itemList;
    }

    public Long buyItem(Item buyItem, Long userId, Long quantity, String currencyType) {
        if (quantity == 0L) {
            return -1L;
        } else {
            itemRepository.purchaseLog(buyItem, userId, quantity, currencyType);
        }

        //Double priceInCurrency = buyItem.getItemPrice() * getExRate(currencyType) * quantity;

        return buyItem.getItemId();
    }
    public Double calcPrice(Item buyItem, Long quantity, Integer currencyType){
        Double priceInCurrency = buyItem.getItemPrice() * getExRate(currencyType) * quantity;
        return priceInCurrency;
    }

    private Double getExRate(Integer currencyType) {
        Optional<ExRate> exRate = exRateRepository.getRatebyCountry(currencyType);
        return exRate.get().getExRate();
    }
    private void authCreator(User user) {
        if (user.getUserType() != USER_TYPE_CREATOR)
            throw new IllegalStateException("상품 작성 권한이 없습니다.");
    }

    private void authEditor(User user) {
        if (user.getUserType() != USER_TYPE_EDITOR)
            throw new IllegalStateException("상품 등록/수정 권한이 없습니다.");
    }

}
